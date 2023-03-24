package io.polyakov.tracy.registry

import io.polyakov.tracy.model.OperationalTrace
import io.polyakov.tracy.model.TraceImpl
import io.polyakov.tracy.model.TraceDescriptor

internal class TraceRegistryImpl : TraceRegistry {

    private val activeTraces = mutableMapOf<String, TraceImpl>()

    override fun createTrace(descriptor: TraceDescriptor): OperationalTrace? {
        if (activeTraces.containsKey(descriptor.name)) {
            return null
        }

        // TODO : replace with factory
        return TraceImpl(descriptor).also {
            activeTraces[descriptor.name] = it
        }
    }

    override fun removeTrace(descriptor: TraceDescriptor): OperationalTrace? {
        return activeTraces.remove(descriptor.name)
    }
}