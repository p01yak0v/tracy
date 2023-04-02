package io.polyakov.tracy.android.matcher

import androidx.fragment.app.Fragment
import io.polyakov.tracy.android.checkpoint.FragmentCheckpoint
import io.polyakov.tracy.model.CheckpointMatcher
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

class FragmentCheckpointDelegate(
    private val targetFragmentClass: KClass<out Fragment>,
    private val targetState: FragmentCheckpoint.FragmentState
) {
    private var matcher: CheckpointMatcher? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): CheckpointMatcher {
        return matcher ?: CheckpointMatcher {
            it is FragmentCheckpoint && it.fragmentClass == targetFragmentClass && it.state == targetState
        }.also { matcher = it }
    }
}