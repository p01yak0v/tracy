package io.polyakov.tracy.dispatcher.stub

import io.polyakov.tracy.dispatcher.TraceStateDelegate
import io.polyakov.tracy.model.Trace

typealias TraceStateChange = Pair<Trace, Trace.State>

class AccumulatorTraceStateDelegate : TraceStateDelegate {

    private val _changes = mutableListOf<TraceStateChange>()
    val changes: List<TraceStateChange> = _changes

    override fun onTraceStateChanged(trace: Trace, state: Trace.State) {
        _changes.add(trace to state)
    }
}
