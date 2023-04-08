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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as ActivityCheckpoint

        if (activityClass != other.activityClass) return false
        if (state != other.state) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + activityClass.hashCode()
        result = 31 * result + state.hashCode()
        return result
    }
}
