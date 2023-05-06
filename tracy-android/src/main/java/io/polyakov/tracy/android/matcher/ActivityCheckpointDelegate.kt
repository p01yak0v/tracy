package io.polyakov.tracy.android.matcher

import android.app.Activity
import io.polyakov.tracy.android.checkpoint.ActivityCheckpoint
import io.polyakov.tracy.matcher.delegate.CheckpointMatcherDelegate
import io.polyakov.tracy.matcher.CheckpointMatcher
import kotlin.reflect.KClass
import kotlin.reflect.KProperty


class ActivityCheckpointDelegate(
    private val targetActivityClass: KClass<out Activity>,
    private val targetState: ActivityCheckpoint.ActivityState
) : CheckpointMatcherDelegate {
    private var matcher: CheckpointMatcher? = null

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): CheckpointMatcher {
        return matcher ?: CheckpointMatcher {
            it is ActivityCheckpoint && it.activityClass == targetActivityClass && it.state == targetState
        }.also { matcher = it }
    }
}
