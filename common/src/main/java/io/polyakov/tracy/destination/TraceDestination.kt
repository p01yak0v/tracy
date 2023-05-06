package io.polyakov.tracy.destination

import io.polyakov.tracy.attribute.TraceAttribute
import io.polyakov.tracy.model.Trace

interface TraceDestination {
    fun start(trace: Trace)
    fun stop(trace: Trace)
    fun cancel(trace: Trace)

    fun fillAttributes(trace: Trace, attrs: List<TraceAttribute<*>>)
}
