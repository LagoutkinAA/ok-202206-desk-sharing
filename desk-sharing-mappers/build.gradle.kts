plugins {
    kotlin("jvm") version "1.7.10"
}

group = rootProject.group
version = rootProject.version

dependencies {
    val datetimeVersion: String by project
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
    implementation(kotlin("stdlib"))
    implementation(project(":desk-sharing-transport-main-openapi"))
    implementation(project(":desk-sharing-common"))

    testImplementation(kotlin("test-junit"))
}