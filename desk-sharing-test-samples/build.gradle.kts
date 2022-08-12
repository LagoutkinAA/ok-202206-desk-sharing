plugins {
    kotlin("jvm") version "1.7.10"
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

val mockkVersion: String by project
val kotestVersion: String by project

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("io.kotest:kotest-framework-engine:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}