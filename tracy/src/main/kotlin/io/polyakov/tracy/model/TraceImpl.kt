package io.polyakov.tracy.model

import java.util.concurrent.TimeUnit

internal class TraceImpl private constructor(
    override val descriptor: TraceDescriptor
) : OperationalTrace {

    internal companion object Factory : TraceFactory {
        override fun create(descriptor: TraceDescriptor) = TraceImpl(descriptor)
    }

    private val _checkpoints = mutableListOf<Checkpoint>()
    override val checkpoints: List<Checkpoint> = _checkpoints

    @Volatile
    override var state: Trace.State = Trace.State.CREATED
        private set

    override val duration: Long
        get() {
            return if (state == Trace.State.STOPPED) {
                TimeUnit.NANOSECONDS.toMillis(
                    _checkpoints.last().creationTimestamp - _checkpoints.first().creationTimestamp
                )
            } else {
                0
            }
        }

    override fun start(startCheckpoint: Checkpoint): Boolean {
        // only created trace could be started
        if (state != Trace.State.CREATED) return false

        state = Trace.State.STARTED
        _checkpoints += startCheckpoint

        return true
    }

    override fun addCheckpoint(intermediateCheckpoint: Checkpoint): Boolean {
        // checkpoints could be added only for started traces
        if (state != Trace.State.STARTED) return false

        _checkpoints += intermediateCheckpoint

        return true
    }

    override fun stop(stopCheckpoint: Checkpoint): Boolean {
        // only started trace could be stopped
        if (state != Trace.State.STARTED) return false

        state = Trace.State.STOPPED
        _checkpoints += stopCheckpoint

        return true
    }

    override fun cancel(cancelCheckpoint: Checkpoint): Boolean {
        // only started trace could be cancelled
        if (state != Trace.State.STARTED) return false

        state = Trace.State.CANCELLED
        _checkpoints += cancelCheckpoint

        return true
    }
}
