package io.polyakov.tracy.matcher.delegate

import io.polyakov.tracy.model.CheckpointMatcher
import kotlin.reflect.KProperty

class CompositeCheckpointMatcherDelegate(
    private val firstDelegate: CheckpointMatcherDelegate,
    private val secondDelegate: CheckpointMatcherDelegate,
    private val operator: (CheckpointMatcher, CheckpointMatcher) -> CheckpointMatcher
) : CheckpointMatcherDelegate {

    override fun getValue(thisRef: Any?, property: KProperty<*>): CheckpointMatcher {

        return operator(
            firstDelegate.getValue(thisRef, property),
            secondDelegate.getValue(thisRef, property)
        )
    }
}
