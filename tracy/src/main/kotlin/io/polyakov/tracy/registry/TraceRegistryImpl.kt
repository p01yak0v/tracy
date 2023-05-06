package io.polyakov.tracy.registry

import io.polyakov.tracy.descriptor.TraceDescriptor
import io.polyakov.tracy.model.OperationalTrace
import io.polyakov.tracy.model.TraceFactory
import java.util.concurrent.ConcurrentHashMap

internal class TraceRegistryImpl(
    private val traceFactory: TraceFactory
) : TraceRegistry {

    private val activeTraces = ConcurrentHashMap<String, OperationalTrace>()

    override fun createTrace(descriptor: TraceDescriptor): OperationalTrace? {
        if (activeTraces.containsKey(descriptor.name)) {
            return null
        }

        val newTrace = traceFactory.create(descriptor)
        val result = activeTraces.putIfAbsent(descriptor.name, newTrace)

        return if (result != null) null else newTrace
    }

    override fun removeTrace(descriptor: TraceDescriptor): OperationalTrace? {
        return activeTraces.remove(descriptor.name)
    }
}
