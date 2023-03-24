package io.polyakov.tracy.model

internal interface OperationalTrace : Trace {
    fun start(startCheckpoint: Checkpoint)
    fun addCheckpoint(intermediateCheckpoint: Checkpoint)
    fun stop(stopCheckpoint: Checkpoint)
    fun cancel(cancelCheckpoint: Checkpoint)
}

internal class TraceImpl internal constructor(
    override val descriptor: TraceDescriptor
) : OperationalTrace {

    private val _checkpoints = mutableListOf<Checkpoint>()
    override val checkpoints: List<Checkpoint> = _checkpoints

    override var state: Trace.State = Trace.State.CREATED
        private set

    override val duration: Long
        get() {
            return if (state == Trace.State.STARTED || state == Trace.State.CREATED) {
                0
            } else {
                _checkpoints.last().creationTimestamp - _checkpoints.first().creationTimestamp
            }
        }

    override fun start(startCheckpoint: Checkpoint) {
        _checkpoints += startCheckpoint
        state = Trace.State.STARTED
    }

    override fun addCheckpoint(intermediateCheckpoint: Checkpoint) {
        _checkpoints += intermediateCheckpoint
    }

    override fun stop(stopCheckpoint: Checkpoint) {
        _checkpoints += stopCheckpoint
        state = Trace.State.STOPPED
    }

    override fun cancel(cancelCheckpoint: Checkpoint) {
        _checkpoints += cancelCheckpoint
        state = Trace.State.CANCELLED
    }
}