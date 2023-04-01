plugins {
    kotlin("jvm") version "1.8.0"
}

dependencies {
    api(project(":common"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}