package io.polyakov.tracy.model.stub

import io.polyakov.tracy.model.Checkpoint
import io.polyakov.tracy.model.OperationalTrace
import io.polyakov.tracy.model.Trace
import io.polyakov.tracy.model.TraceDescriptor

internal class StubOperationalTrace(
    override val descriptor: TraceDescriptor
) : OperationalTrace {

    override fun start(startCheckpoint: Checkpoint) = Unit
    override fun addCheckpoint(intermediateCheckpoint: Checkpoint) = Unit
    override fun stop(stopCheckpoint: Checkpoint) = Unit

    override fun cancel(cancelCheckpoint: Checkpoint) = Unit

    override val checkpoints: List<Checkpoint> = emptyList()
    override val state: Trace.State = Trace.State.CREATED
    override val duration: Long = 0
}
