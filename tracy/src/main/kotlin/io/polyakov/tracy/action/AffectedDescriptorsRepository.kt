package io.polyakov.tracy.action

import io.polyakov.tracy.descriptor.TraceDescriptor
import io.polyakov.tracy.model.Checkpoint

internal typealias AffectedDescriptor = Pair<TraceDescriptor, TraceAction>

internal interface AffectedDescriptorsRepository {
    fun getAffectedTraces(checkpoint: Checkpoint): List<AffectedDescriptor>
}
