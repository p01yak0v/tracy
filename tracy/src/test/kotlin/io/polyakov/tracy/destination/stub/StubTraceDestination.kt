package io.polyakov.tracy.destination.stub

import io.polyakov.tracy.destination.TraceDestination
import io.polyakov.tracy.model.Trace
import io.polyakov.tracy.model.TraceAttribute

internal open class StubTraceDestination : TraceDestination {

    private val _startedTraces = mutableListOf<Trace>()
    val startedTraces: List<Trace> = _startedTraces

    private val _stoppedTraces = mutableListOf<Trace>()
    val stoppedTraces: List<Trace> = _stoppedTraces

    private val _cancelledTraces = mutableListOf<Trace>()
    val cancelledTraces: List<Trace> = _cancelledTraces

    private val _attributeEvents = mutableListOf<Trace>()
    val attributeEvents: List<Trace> = _attributeEvents

    override fun start(trace: Trace) {
        _startedTraces += trace
    }

    override fun stop(trace: Trace) {
        _stoppedTraces += trace
    }

    override fun cancel(trace: Trace) {
        _cancelledTraces += trace
    }

    override fun fillAttributes(trace: Trace, attrs: List<TraceAttribute<*>>) {
        _attributeEvents += trace
    }
}
