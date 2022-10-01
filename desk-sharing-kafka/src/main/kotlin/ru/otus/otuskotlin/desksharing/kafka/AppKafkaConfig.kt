package ru.otus.otuskotlin.desksharing.kafka

class AppKafkaConfig(
    val kafkaHosts: List<String> = KAFKA_HOSTS,
    val kafkaGroupId: String = KAFKA_GROUP_ID,
    val kafkaTopicIn: String = KAFKA_TOPIC_IN,
    val kafkaTopicOut: String = KAFKA_TOPIC_OUT
) {
    companion object {
        const val KAFKA_HOST_VAR = "KAFKA_HOSTS"
        const val KAFKA_TOPIC_IN_V1_VAR = "KAFKA_TOPIC_IN"
        const val KAFKA_TOPIC_OUT_V1_VAR = "KAFKA_TOPIC_OUT"
        const val KAFKA_GROUP_ID_VAR = "KAFKA_GROUP_ID"

        val KAFKA_HOSTS by lazy { (System.getenv(KAFKA_HOST_VAR) ?: "").split("\\s*[,;]\\s*") }
        val KAFKA_GROUP_ID by lazy { System.getenv(KAFKA_GROUP_ID_VAR) ?: "desk-sharing" }
        val KAFKA_TOPIC_IN by lazy { System.getenv(KAFKA_TOPIC_IN_V1_VAR) ?: "desk-sharing-in" }
        val KAFKA_TOPIC_OUT by lazy { System.getenv(KAFKA_TOPIC_OUT_V1_VAR) ?: "desk-sharing-out" }
    }
}
