package io.polyakov.tracy.attribute

import io.polyakov.tracy.model.Checkpoint

class EmptyAttributeExtractor : AttributeExtractor {
    override fun extract(checkpoint: Checkpoint): List<TraceAttribute<*>> = emptyList()
}
