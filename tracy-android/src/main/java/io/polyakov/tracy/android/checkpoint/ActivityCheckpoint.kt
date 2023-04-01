package io.polyakov.tracy.android.checkpoint

import android.app.Activity
import io.polyakov.tracy.model.Checkpoint
import kotlin.reflect.KClass

class ActivityCheckpoint(
    val activityClass: KClass<out Activity>,
    val state: ActivityState
) : Checkpoint("activity-state-checkpoint") {
    enum class ActivityState {
        CREATED, DESTROYED,
        STARTED, STOPPED,
        RESUMED, PAUSED
    }
}