plugins {
    kotlin("jvm") version "1.8.0"
    application
}

dependencies {
    implementation(project(":tracy"))
    implementation(project(":sample-common"))
}

application {
    mainClass.set("io.polyakov.tracy.sample.jvm.MainKt")
}