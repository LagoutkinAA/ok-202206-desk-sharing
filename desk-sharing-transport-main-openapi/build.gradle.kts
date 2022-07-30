plugins {
    kotlin("jvm") version "1.7.10"
}

group = "ru.otus.otuskotlin.deskSharing"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}