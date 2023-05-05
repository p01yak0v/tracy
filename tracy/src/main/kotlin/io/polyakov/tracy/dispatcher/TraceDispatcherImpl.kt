package io.polyakov.tracy.dispatcher

import io.polyakov.tracy.model.Checkpoint
import io.polyakov.tracy.model.OperationalTrace
import io.polyakov.tracy.model.Trace.State

internal class TraceDispatcherImpl(
    private val traceStateDelegate: TraceStateDelegate
) : TraceDispatcher {

    override fun dispatchStart(trace: OperationalTrace, startCheckpoint: Checkpoint) {
        if (trace.state != State.CREATED) {
            return
        }

        if (trace.start(startCheckpoint)) {
            traceStateDelegate.onTraceStateChanged(trace, State.STARTED)
        }
    }

    override fun dispatchCancel(trace: OperationalTrace, cancelCheckpoint: Checkpoint) {
        if (trace.state != State.STARTED) {
            return
        }

        if (trace.cancel(cancelCheckpoint)) {
            traceStateDelegate.onTraceStateChanged(trace, State.CANCELLED)
        }
    }

    override fun dispatchMediate(trace: OperationalTrace, intermediateCheckpoint: Checkpoint) {
        if (trace.state != State.STARTED) {
            return
        }

        trace.addCheckpoint(intermediateCheckpoint)
    }

    override fun dispatchStop(trace: OperationalTrace, stopCheckpoint: Checkpoint) {
        if (trace.state != State.STARTED) {
            return
        }

        if (trace.stop(stopCheckpoint)) {
            traceStateDelegate.onTraceStateChanged(trace, State.STOPPED)
        }
    }
}
