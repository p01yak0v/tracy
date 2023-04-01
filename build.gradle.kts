// TODO: Remove once https://youtrack.jetbrains.com/issue/KTIJ-19369 is fixed
// check other build.gradle.kts too
@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.application) apply false
}

group = "io.polyakov"
version = "1.0-SNAPSHOT"