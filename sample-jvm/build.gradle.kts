@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

dependencies {
    implementation(project(":tracy"))
    implementation(project(":sample-common"))
}

application {
    mainClass.set("io.polyakov.tracy.sample.jvm.MainKt")
}