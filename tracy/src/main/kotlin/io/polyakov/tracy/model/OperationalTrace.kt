package io.polyakov.tracy.model

internal interface OperationalTrace : Trace {
    fun start(startCheckpoint: Checkpoint): Boolean
    fun addCheckpoint(intermediateCheckpoint: Checkpoint): Boolean
    fun stop(stopCheckpoint: Checkpoint): Boolean
    fun cancel(cancelCheckpoint: Checkpoint): Boolean
}
