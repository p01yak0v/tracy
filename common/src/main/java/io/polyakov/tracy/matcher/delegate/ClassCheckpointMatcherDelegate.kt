package io.polyakov.tracy.matcher.delegate

import io.polyakov.tracy.matcher.CheckpointMatcher
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

class ClassCheckpointMatcherDelegate(
    private val targetClass: KClass<*>
) : CachingCheckpointMatcherDelegate() {

    override fun createMatcher(thisRef: Any?, property: KProperty<*>) = CheckpointMatcher {
        it::class == targetClass
    }
}

inline fun <reified T> classMatcher(): ClassCheckpointMatcherDelegate {
    return ClassCheckpointMatcherDelegate(T::class)
}
