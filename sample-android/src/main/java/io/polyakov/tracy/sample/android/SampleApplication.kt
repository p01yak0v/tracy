package io.polyakov.tracy.sample.android

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import io.polyakov.sample.common.descriptor.SampleDescriptorProvider
import io.polyakov.tracy.Tracy
import io.polyakov.tracy.android.destintation.SystemTraceDestination
import io.polyakov.tracy.android.enableActivityCheckpoints
import io.polyakov.tracy.android.enableForegroundCheckpoints
import io.polyakov.tracy.android.firebase.FirebaseDestination
import io.polyakov.tracy.destination.LoggingDestination
import io.polyakov.tracy.destination.TraceDestination

private const val LOGGING_TAG = "LoggingAndroid"

open class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTracy()
    }

    private fun setupTracy() = with(Tracy) {
        init {
            traceProvider {
                SampleAndroidDescriptorProvider(
                    SampleDescriptorProvider()
                )
            }
            destinations {
                provideDestinationSet()
            }
        }
        enableForegroundCheckpoints()
        enableActivityCheckpoints(
            application = this@SampleApplication,
            includeFragmentsCheckpoint = true
        )
    }

    open protected fun provideDestinationSet(): Set<TraceDestination> = setOf(
        SystemTraceDestination(),
        LoggingDestination(LOGGING_TAG),
        FirebaseDestination(Firebase.performance)
    )
}
