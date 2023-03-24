package io.polyakov.tracy.destination

import io.polyakov.tracy.model.Trace
import io.polyakov.tracy.model.TraceAttribute

interface TraceDestination {
    fun start(trace: Trace)
    fun stop(trace: Trace)
    fun cancel(trace: Trace)

    fun fillAttributes(trace: Trace, attrs: List<TraceAttribute<*>>)
}