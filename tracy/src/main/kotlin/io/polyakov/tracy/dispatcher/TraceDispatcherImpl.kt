package io.polyakov.tracy.dispatcher

import io.polyakov.tracy.model.Checkpoint
import io.polyakov.tracy.model.OperationalTrace
import io.polyakov.tracy.model.Trace

internal class TraceDispatcherImpl(
    private val traceStateDelegate: TraceStateDelegate
) : TraceDispatcher {

    override fun dispatchStart(trace: OperationalTrace, startCheckpoint: Checkpoint) {
        if (trace.state != Trace.State.CREATED) {
            return
        }

        trace.start(startCheckpoint)

        traceStateDelegate.onTraceStarted(trace)
    }

    override fun dispatchCancel(trace: OperationalTrace, cancelCheckpoint: Checkpoint) {
        if (trace.state != Trace.State.STARTED) {
            return
        }

        trace.cancel(cancelCheckpoint)

        traceStateDelegate.onTraceCancelled(trace)
    }

    override fun dispatchMediate(trace: OperationalTrace, intermediateCheckpoint: Checkpoint) {
        if (trace.state != Trace.State.STARTED) {
            return
        }

        trace.addCheckpoint(intermediateCheckpoint)

        traceStateDelegate.onTraceMediated(trace)
    }

    override fun dispatchStop(trace: OperationalTrace, stopCheckpoint: Checkpoint) {
        if (trace.state != Trace.State.STARTED) {
            return
        }

        trace.stop(stopCheckpoint)

        traceStateDelegate.onTraceStopped(trace)
    }
}