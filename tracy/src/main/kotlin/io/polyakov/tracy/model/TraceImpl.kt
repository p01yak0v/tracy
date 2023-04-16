package io.polyakov.tracy.model

internal class TraceImpl private constructor(
    override val descriptor: TraceDescriptor
) : OperationalTrace {

    internal companion object Factory : TraceFactory {
        override fun create(descriptor: TraceDescriptor) = TraceImpl(descriptor)
    }

    private val _checkpoints = mutableListOf<Checkpoint>()
    override val checkpoints: List<Checkpoint> = _checkpoints

    override var state: Trace.State = Trace.State.CREATED
        private set

    override val duration: Long
        get() {
            return if (state == Trace.State.STOPPED) {
                _checkpoints.last().creationTimestamp - _checkpoints.first().creationTimestamp
            } else {
                0
            }
        }

    override fun start(startCheckpoint: Checkpoint) {
        check(state == Trace.State.CREATED) {
            "You cannot start a trace which is not in ${Trace.State.CREATED} state"
        }

        _checkpoints += startCheckpoint
        state = Trace.State.STARTED
    }

    override fun addCheckpoint(intermediateCheckpoint: Checkpoint) {
        _checkpoints += intermediateCheckpoint
    }

    override fun stop(stopCheckpoint: Checkpoint) {
        check(state == Trace.State.STARTED) {
            "You cannot stop a trace which is not in ${Trace.State.STARTED} state"
        }

        _checkpoints += stopCheckpoint
        state = Trace.State.STOPPED
    }

    override fun cancel(cancelCheckpoint: Checkpoint) {
        check(state == Trace.State.STARTED) {
            "You cannot cancel a trace which is not in ${Trace.State.STARTED} state"
        }

        _checkpoints += cancelCheckpoint
        state = Trace.State.CANCELLED
    }
}
