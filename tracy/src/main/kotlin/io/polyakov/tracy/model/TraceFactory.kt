package io.polyakov.tracy.model

import io.polyakov.tracy.descriptor.TraceDescriptor

internal interface TraceFactory {
    fun create(descriptor: TraceDescriptor): OperationalTrace
}
