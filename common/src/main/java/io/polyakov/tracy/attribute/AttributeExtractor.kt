package io.polyakov.tracy.attribute

import io.polyakov.tracy.model.Checkpoint

fun interface AttributeExtractor {
    fun extract(checkpoint: Checkpoint): List<TraceAttribute<*>>
}
