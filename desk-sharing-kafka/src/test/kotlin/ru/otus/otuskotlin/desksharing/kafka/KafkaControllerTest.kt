package ru.otus.otuskotlin.desksharing.kafka

import apiV1RequestSerialize
import apiV1ResponseDeserialize
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandApiStatus
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandCreateObjectDto
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandCreateRequest
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandCreateResponse
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandDebug
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandRequestDebugMode
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandRequestDebugStubs
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

class KafkaControllerTest {

    @Test
    fun runKafka() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer<String, String>(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig()
        val inputTopic = config.kafkaTopicIn
        val outputTopic = config.kafkaTopicOut

        val app = AppKafkaConsumer(config, listOf(ConsumerStrategy()), consumer = consumer, producer = producer)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "test-1",
                    apiV1RequestSerialize(
                        DemandCreateRequest(
                            requestId = "2e07327d-47e7-4da1-9c89-eff53a37cdb7",
                            debug = DemandDebug(
                                mode = DemandRequestDebugMode.STUB,
                                stub = DemandRequestDebugStubs.SUCCESS
                            ),
                            demand = DemandCreateObjectDto(
                                date = "2022-01-01",
                                bookingDate = "2022-01-10",
                                employeeId = "2e07327d-47e7-4da1-9c89-eff53a37c000",
                                status = DemandApiStatus.NEW
                            )
                        )
                    )
                )
            )
            app.stop()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(inputTopic, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.run()

        val message = producer.history().first()
        val result = apiV1ResponseDeserialize<DemandCreateResponse>(message.value())
        assertEquals(outputTopic, message.topic())
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cdb7", result.requestId)
        assertNull(result.errors)
        assertEquals(DemandApiStatus.ACCEPTED, result.demand?.status)
    }

    companion object {
        const val PARTITION = 0
    }
}


