plugins {
    kotlin("jvm")
}

dependencies {
    val datetimeVersion: String by project
    val coroutinesVersion: String by project
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    implementation(kotlin("test-junit"))

    implementation(project(":desk-sharing-common"))
}