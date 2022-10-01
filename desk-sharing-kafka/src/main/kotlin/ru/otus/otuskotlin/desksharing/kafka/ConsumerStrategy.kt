package ru.otus.otuskotlin.desksharing.kafka

import apiV1RequestDeserialize
import apiV1ResponseSerialize
import fromTransport
import ru.otus.otuskotlin.deskSharing.api.v1.models.IRequest
import ru.otus.otuskotlin.deskSharing.api.v1.models.IResponse
import ru.otus.otuskotlin.desksharing.common.DemandContext
import toTransportDemand

class ConsumerStrategy : IConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicIn, config.kafkaTopicOut)
    }

    override fun serialize(source: DemandContext): String {
        val response: IResponse = source.toTransportDemand()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: DemandContext) {
        val request: IRequest = apiV1RequestDeserialize(value)
        target.fromTransport(request)
    }
}