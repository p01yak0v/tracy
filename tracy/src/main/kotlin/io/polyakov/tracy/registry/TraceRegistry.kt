package io.polyakov.tracy.registry

import io.polyakov.tracy.model.OperationalTrace
import io.polyakov.tracy.descriptor.TraceDescriptor

internal interface TraceRegistry {
    fun createTrace(descriptor: TraceDescriptor): OperationalTrace?
    fun removeTrace(descriptor: TraceDescriptor): OperationalTrace?
}
