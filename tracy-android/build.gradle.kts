plugins {
    id("tracy-android-library")
}

android {
    namespace = "io.polyakov.tracy.android"
}

dependencies {
    api(project(":tracy"))

    implementation(libs.androidx.fragment)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.tracing)
}