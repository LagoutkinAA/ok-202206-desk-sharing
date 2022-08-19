import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "ru.otus.otuskotlin.deskSharing"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}


dependencies {
    val datetimeVersion: String by project
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}
