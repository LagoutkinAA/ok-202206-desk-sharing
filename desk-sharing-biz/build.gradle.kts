plugins {
    kotlin("jvm")
}

dependencies {
    val datetimeVersion: String by project
    val coroutinesVersion: String by project
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation(project(":desk-sharing-common"))
    implementation(project(":desk-sharing-stub"))
    implementation(project(":desk-sharing-lib-cor"))
    implementation(project(":desk-sharing-auth"))

    testImplementation(kotlin("test-junit"))
    testImplementation(kotlin("test-common"))
    testImplementation(kotlin("test-annotations-common"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")

    testImplementation(project(":desk-sharing-repository-stub"))
    testImplementation(project(":desk-sharing-repository-tests"))
    testImplementation(project(":desk-sharing-repository-inmemory"))
}