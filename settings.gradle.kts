
rootProject.name = "ok-202206-desk-sharing"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val kotestVersion: String by settings
        val openapiVersion: String by settings

        kotlin("jvm") version kotlinVersion apply false
        kotlin("multiplatform") version kotlinVersion apply false
        id("io.kotest.multiplatform") version kotestVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false

    }
}

include("desk-sharing-common")
include("desk-sharing-transport-main-openapi")
include("desk-sharing-test-samples")
include("desk-sharing-mappers")
