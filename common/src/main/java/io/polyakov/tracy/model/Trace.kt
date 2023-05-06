package io.polyakov.tracy.model

import io.polyakov.tracy.descriptor.TraceDescriptor

interface Trace {
    val descriptor: TraceDescriptor
    val checkpoints: List<Checkpoint>

    val state: State
    val duration: Long

    enum class State { CREATED, STARTED, CANCELLED, STOPPED }
}
