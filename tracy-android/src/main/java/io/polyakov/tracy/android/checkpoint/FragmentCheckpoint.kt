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
}