plugins {
    kotlin("jvm") version "1.8.0"
    application
}

dependencies {
    implementation(project(":tracy"))
}

application {
    mainClass.set("io.polyakov.tracy.sample.jvm.MainKt")
}