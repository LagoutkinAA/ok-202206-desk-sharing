plugins {
    kotlin("jvm") version "1.7.10"
}

group = rootProject.group
version = rootProject.version

dependencies {
    val datetimeVersion: String by project
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
}
