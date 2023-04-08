package io.polyakov.tracy.android

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import io.polyakov.tracy.Tracy

fun Tracy.Companion.enableActivityCheckpoints(
    application: Application,
    includeFragmentsCheckpoint: Boolean
) {
    application.registerActivityLifecycleCallbacks(
        TracyActivityCallbacks(
            isFragmentRequired = includeFragmentsCheckpoint
        )
    )
}

fun Tracy.Companion.enableForegroundCheckpoints() {
    ProcessLifecycleOwner.get().lifecycle.addObserver(ForegroundObserver())
}
