plugins {
    kotlin("jvm")
}

dependencies {
    val datetimeVersion: String by project
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
    implementation(kotlin("stdlib"))
    implementation(project(":desk-sharing-transport-main-openapi"))
    implementation(project(":desk-sharing-common"))

    testImplementation(kotlin("test-junit"))
}