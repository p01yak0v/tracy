package io.polyakov.tracy.matcher.delegate

import io.polyakov.tracy.matcher.CheckpointMatcher
import kotlin.reflect.KProperty

abstract class CachingCheckpointMatcherDelegate : CheckpointMatcherDelegate {

    private var matcher: CheckpointMatcher? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): CheckpointMatcher {
        return matcher ?: createMatcher(thisRef, property).also { matcher = it }
    }

    abstract fun createMatcher(thisRef: Any?, property: KProperty<*>): CheckpointMatcher
}
