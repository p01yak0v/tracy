@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("tracy-jvm-convention")
    application
}

dependencies {
    implementation(project(":tracy"))
    implementation(project(":sample-common"))
}

application {
    mainClass.set("io.polyakov.tracy.sample.jvm.MainKt")
}