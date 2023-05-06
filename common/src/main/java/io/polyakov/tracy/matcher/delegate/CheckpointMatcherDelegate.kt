package io.polyakov.tracy.matcher.delegate

import io.polyakov.tracy.matcher.AllCompositeCheckpointMatcher
import io.polyakov.tracy.matcher.AnyCompositeCheckpointMatcher
import io.polyakov.tracy.matcher.CheckpointMatcher
import kotlin.reflect.KProperty

interface CheckpointMatcherDelegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): CheckpointMatcher

    infix fun and(additionalDelegate: CheckpointMatcherDelegate): CheckpointMatcherDelegate {
        return CompositeCheckpointMatcherDelegate(
            firstDelegate = this,
            secondDelegate = additionalDelegate,
            operator = ::AllCompositeCheckpointMatcher
        )
    }

    infix fun or(additionalDelegate: CheckpointMatcherDelegate): CheckpointMatcherDelegate {
        return CompositeCheckpointMatcherDelegate(
            firstDelegate = this,
            secondDelegate = additionalDelegate,
            operator = ::AnyCompositeCheckpointMatcher
        )
    }
}
