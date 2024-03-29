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
    implementation(libs.androidx.splash)
    implementation(libs.androidx.uiautomator)
    implementation(libs.firebase.performance)
    implementation(libs.google.material)
    implementation(libs.kotlin.reflect)

    testImplementation(libs.test.junit4)
    testImplementation(libs.test.robolectric)
    testImplementation(testFixtures(project(":tracy")))

    testRuntimeOnly(libs.test.junit.vintage.engine)
}
