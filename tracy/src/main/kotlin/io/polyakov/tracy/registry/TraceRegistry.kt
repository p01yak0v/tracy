package io.polyakov.tracy.registry

import io.polyakov.tracy.descriptor.TraceDescriptor
import io.polyakov.tracy.model.OperationalTrace

internal interface TraceRegistry {
    fun createTrace(descriptor: TraceDescriptor): OperationalTrace?
    fun getTrace(descriptor: TraceDescriptor): OperationalTrace?
    fun removeTrace(descriptor: TraceDescriptor): OperationalTrace?
}
