
rootProject.name = "ok-202206-desk-sharing"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val kotestVersion: String by settings
        val openapiVersion: String by settings
        val springframeworkBootVersion: String by settings
        val springDependencyManagementVersion: String by settings
        val pluginSpringVersion: String by settings
        val pluginJpa: String by settings

        kotlin("jvm") version kotlinVersion apply false
        kotlin("multiplatform") version kotlinVersion apply false
        id("io.kotest.multiplatform") version kotestVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false

        id("org.springframework.boot") version springframeworkBootVersion apply false
        id("io.spring.dependency-management") version springDependencyManagementVersion apply false
        kotlin("plugin.spring") version pluginSpringVersion apply false
        kotlin("plugin.jpa") version pluginJpa apply false

    }
}

include("desk-sharing-common")
include("desk-sharing-transport-main-openapi")
include("desk-sharing-test-samples")
include("desk-sharing-mappers")
include("desk-sharing-entity")
include("desk-sharing-app-spring")
include("desk-sharing-stub")
include("desk-sharing-lib-cor")
include("desk-sharing-lib-validation")
include("desk-sharing-biz")
