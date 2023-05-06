package io.polyakov.tracy.matcher.delegate

import io.polyakov.tracy.matcher.CheckpointMatcher
import kotlin.reflect.KProperty

class NoneCheckpointMatcherDelegate : CachingCheckpointMatcherDelegate() {
    override fun createMatcher(thisRef: Any?, property: KProperty<*>) = CheckpointMatcher { false }
}

fun none() = NoneCheckpointMatcherDelegate()
