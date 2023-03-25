package io.polyakov.tracy.action

import io.polyakov.tracy.model.Checkpoint
import io.polyakov.tracy.model.TraceDescriptor

internal typealias AffectedDescriptor=Pair<TraceDescriptor, TraceAction>

internal interface AffectedDescriptorsRepository {
    fun getAffectedTraces(checkpoint: Checkpoint): List<AffectedDescriptor>
}