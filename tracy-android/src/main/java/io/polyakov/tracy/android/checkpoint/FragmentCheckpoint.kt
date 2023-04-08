package io.polyakov.tracy.android.checkpoint

import androidx.fragment.app.Fragment
import io.polyakov.tracy.model.Checkpoint
import kotlin.reflect.KClass

class FragmentCheckpoint(
    val fragmentClass: KClass<out Fragment>,
    val state: FragmentState
) : Checkpoint("fragment-state-checkpoint") {
    enum class FragmentState {
        ATTACHED, DETACHED,
        CREATED, DESTROYED,
        PAUSED, RESUMED,
        STARTED, STOPPED,
        VIEW_CREATED, VIEW_DESTROYED
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as FragmentCheckpoint

        if (fragmentClass != other.fragmentClass) return false
        if (state != other.state) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + fragmentClass.hashCode()
        result = 31 * result + state.hashCode()
        return result
    }
}
