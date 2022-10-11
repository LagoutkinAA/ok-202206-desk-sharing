package ru.otus.otuskotlin.desksharing.kafka

fun main() {
    val config = AppKafkaConfig()
    val consumer = AppKafkaConsumer(config, listOf(ConsumerStrategy()))
    consumer.run()
}
