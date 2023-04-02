package io.polyakov.tracy.android

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.polyakov.tracy.Tracy
import io.polyakov.tracy.android.checkpoint.BackgroundCheckpoint
import io.polyakov.tracy.android.checkpoint.ForegroundCheckpoint

internal class ForegroundObserver : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        Tracy.pass(ForegroundCheckpoint)
    }

    override fun onStop(owner: LifecycleOwner) {
        Tracy.pass(BackgroundCheckpoint)
    }
}