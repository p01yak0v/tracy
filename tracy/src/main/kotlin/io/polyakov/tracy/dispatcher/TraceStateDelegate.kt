package io.polyakov.tracy.dispatcher

import io.polyakov.tracy.model.Trace

interface TraceStateDelegate {
    fun onTraceStarted(trace: Trace) = Unit
    fun onTraceStopped(trace: Trace) = Unit
    fun onTraceCancelled(trace: Trace) = Unit
    fun onTraceMediated(trace: Trace) = Unit
}