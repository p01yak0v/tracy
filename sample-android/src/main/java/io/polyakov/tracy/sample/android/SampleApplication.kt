package io.polyakov.tracy.sample.android

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import io.polyakov.sample.common.descriptor.SampleDescriptorProvider
import io.polyakov.tracy.Tracy
import io.polyakov.tracy.android.firebase.FirebaseDestination
import io.polyakov.tracy.android.enableActivityCheckpoints
import io.polyakov.tracy.android.enableForegroundCheckpoints
import io.polyakov.tracy.destination.LoggingDestination

private const val LOGGING_TAG = "LoggingAndroid"

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTracy()
    }

    private fun setupTracy() = with(Tracy) {
        init(
            SampleAndroidDescriptorProvider(
                SampleDescriptorProvider()
            ),
            listOf(
                LoggingDestination(LOGGING_TAG),
                FirebaseDestination(Firebase.performance)
            )
        )
        enableForegroundCheckpoints()
        enableActivityCheckpoints(
            application = this@SampleApplication,
            includeFragmentsCheckpoint = true
        )
    }
}
