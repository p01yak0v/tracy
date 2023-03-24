package io.polyakov.tracy.dispatcher

import io.polyakov.tracy.model.Trace

interface TraceStateDelegate {
    fun onTraceStateChanged(trace: Trace, state: Trace.State)
}