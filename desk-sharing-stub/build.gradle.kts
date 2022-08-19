plugins {
    kotlin("jvm")
}

dependencies {
    val datetimeVersion: String by project
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
    implementation(kotlin("stdlib"))

    implementation(project(":desk-sharing-common"))
}
