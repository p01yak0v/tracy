package io.polyakov.tracy.model

internal interface TraceFactory {
    fun create(descriptor: TraceDescriptor): OperationalTrace
}
