package io.polyakov.tracy.model

interface TraceDescriptor {
    val startMatcher: CheckpointMatcher
    val stopMatcher: CheckpointMatcher
    val cancelMatcher: CheckpointMatcher

    val name: String

    val attributeExtractor: AttributeExtractor
}

fun interface AttributeExtractor {
    fun extract(checkpoint: Checkpoint): List<TraceAttribute<*>>
}

sealed class TraceAttribute<T : Any>(val name: String, val value: T) {
    class LongAttribute(name: String, value: Long) : TraceAttribute<Long>(name, value)
    class StringAttribute(name: String, value: String) : TraceAttribute<String>(name, value)
    class FloatAttribute(name: String, value: Float) : TraceAttribute<Float>(name, value)
}

fun String.asAttribute(name: String) = TraceAttribute.StringAttribute(name, this)

fun Float.asAttribute(name: String) = TraceAttribute.FloatAttribute(name, this)

fun Long.asAttribute(name: String) = TraceAttribute.LongAttribute(name, this)

infix fun String.attr(value: String) = TraceAttribute.StringAttribute(this, value)

infix fun String.attr(value: Float) = TraceAttribute.FloatAttribute(this, value)

infix fun String.attr(value: Long) = TraceAttribute.LongAttribute(this, value)