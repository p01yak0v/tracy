plugins {
    id("tracy-android-app")
    id("com.google.gms.google-services")
}

android {
    namespace = "io.polyakov.tracy.sample.android"

    defaultConfig {
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(project(":tracy-android"))
    implementation(project(":tracy-firebase"))
    implementation(project(":sample-common"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintLayout)
    implementation(libs.firebase.performance)
}