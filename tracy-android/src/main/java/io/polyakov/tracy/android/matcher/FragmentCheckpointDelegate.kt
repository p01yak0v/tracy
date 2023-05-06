package io.polyakov.tracy.android.matcher

import androidx.fragment.app.Fragment
import io.polyakov.tracy.android.checkpoint.FragmentCheckpoint
import io.polyakov.tracy.matcher.CheckpointMatcher
import io.polyakov.tracy.matcher.delegate.CachingCheckpointMatcherDelegate
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

class FragmentCheckpointDelegate(
    private val targetFragmentClass: KClass<out Fragment>,
    private val targetState: FragmentCheckpoint.FragmentState
) : CachingCheckpointMatcherDelegate() {

    override fun createMatcher(thisRef: Any?, property: KProperty<*>) = CheckpointMatcher {
        it is FragmentCheckpoint && it.fragmentClass == targetFragmentClass && it.state == targetState
    }
}
