package io.polyakov.tracy.model.stub

import io.polyakov.tracy.model.Checkpoint
import io.polyakov.tracy.model.OperationalTrace
import io.polyakov.tracy.model.Trace
import io.polyakov.tracy.descriptor.TraceDescriptor

internal class StubOperationalTrace(
    override val descriptor: TraceDescriptor
) : OperationalTrace {

    override fun start(startCheckpoint: Checkpoint) = true
    override fun addCheckpoint(intermediateCheckpoint: Checkpoint) = true
    override fun stop(stopCheckpoint: Checkpoint) = true

    override fun cancel(cancelCheckpoint: Checkpoint) = true

    override val checkpoints: List<Checkpoint> = emptyList()
    override val state: Trace.State = Trace.State.CREATED
    override val duration: Long = 0
}
