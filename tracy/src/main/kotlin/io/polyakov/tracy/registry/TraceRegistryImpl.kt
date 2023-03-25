package io.polyakov.tracy.registry

import io.polyakov.tracy.model.*
import io.polyakov.tracy.model.OperationalTrace

internal class TraceRegistryImpl(
    private val traceFactory: TraceFactory
) : TraceRegistry {

    private val activeTraces = mutableMapOf<String, OperationalTrace>()

    override fun createTrace(descriptor: TraceDescriptor): OperationalTrace? {
        if (activeTraces.containsKey(descriptor.name)) {
            return null
        }

        return traceFactory.create(descriptor).also {
            activeTraces[descriptor.name] = it
        }
    }

    override fun removeTrace(descriptor: TraceDescriptor): OperationalTrace? {
        return activeTraces.remove(descriptor.name)
    }
}