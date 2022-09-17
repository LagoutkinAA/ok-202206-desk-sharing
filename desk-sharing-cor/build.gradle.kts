plugins {
    kotlin("jvm")
}

dependencies {
    val datetimeVersion: String by project
    val coroutinesVersion: String by project
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    testImplementation(kotlin("test-junit"))
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
}
