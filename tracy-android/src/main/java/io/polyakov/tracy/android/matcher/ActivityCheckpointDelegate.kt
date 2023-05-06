package io.polyakov.tracy.android.matcher

import android.app.Activity
import io.polyakov.tracy.android.checkpoint.ActivityCheckpoint
import io.polyakov.tracy.matcher.CheckpointMatcher
import io.polyakov.tracy.matcher.delegate.CachingCheckpointMatcherDelegate
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

class ActivityCheckpointDelegate(
    private val targetActivityClass: KClass<out Activity>,
    private val targetState: ActivityCheckpoint.ActivityState
) : CachingCheckpointMatcherDelegate() {

    override fun createMatcher(thisRef: Any?, property: KProperty<*>) = CheckpointMatcher {
        it is ActivityCheckpoint && it.activityClass == targetActivityClass && it.state == targetState
    }
}
