plugins {
    id("tracy-android-library")
}

android {
    namespace = "io.polyakov.tracy.android.firebase"
}

dependencies {
    // internal projects
    implementation(project(":common"))

    implementation(libs.firebase.performance)
}