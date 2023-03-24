plugins {
    kotlin("jvm") version "1.8.0"
}

dependencies {
    api(project(":common"))

    implementation(kotlin("reflect"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}