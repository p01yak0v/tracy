package io.polyakov.tracy.model

interface Trace {
    val descriptor: TraceDescriptor
    val checkpoints: List<Checkpoint>

    val state: State
    val duration: Long

    enum class State { CREATED, STARTED, CANCELLED, STOPPED }
}