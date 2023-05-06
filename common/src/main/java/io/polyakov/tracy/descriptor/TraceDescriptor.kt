package io.polyakov.tracy.descriptor

import io.polyakov.tracy.attribute.AttributeExtractor
import io.polyakov.tracy.attribute.EmptyAttributeExtractor
import io.polyakov.tracy.destination.TraceDestination
import io.polyakov.tracy.matcher.CheckpointMatcher
import kotlin.reflect.KClass

interface TraceDescriptor {
    val startMatcher: CheckpointMatcher
    val stopMatcher: CheckpointMatcher
    val cancelMatcher: CheckpointMatcher

    val name: String

    val attributeExtractor: AttributeExtractor
        get() = EmptyAttributeExtractor()

    val excludedDestinations: List<KClass<out TraceDestination>>
        get() = emptyList()
}
