package io.polyakov.tracy.attribute

sealed class TraceAttribute<T : Any>(val name: String, val value: T) {
    class LongAttribute(name: String, value: Long) : TraceAttribute<Long>(name, value)
    class StringAttribute(name: String, value: String) : TraceAttribute<String>(name, value)
    class FloatAttribute(name: String, value: Float) : TraceAttribute<Float>(name, value)
}
