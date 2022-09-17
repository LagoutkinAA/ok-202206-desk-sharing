plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")
}

dependencies {
    val kotestVersion: String by project
    val springdocOpenapiUiVersion: String by project

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springdoc:springdoc-openapi-ui:$springdocOpenapiUiVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    val datetimeVersion: String by project
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")

    // project modules
    implementation(project(":desk-sharing-common"))
    implementation(project(":desk-sharing-mappers"))
    implementation(project(":desk-sharing-transport-main-openapi"))
    implementation(project(":desk-sharing-stub"))
    implementation(project(":desk-sharing-biz"))

    // tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("com.ninja-squad:springmockk:3.0.1")
}

tasks {
    withType<ProcessResources> {
        from("$rootDir/specification") {
            into("/static")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
