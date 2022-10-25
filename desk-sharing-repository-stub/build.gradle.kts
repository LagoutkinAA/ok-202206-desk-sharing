plugins {
    kotlin("jvm")
}

dependencies {
    val datetimeVersion: String by project
    val coroutinesVersion: String by project
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesVersion}")
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":desk-sharing-common"))
    implementation(project(":desk-sharing-stub"))
}