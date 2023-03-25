package io.polyakov.tracy.model

internal interface OperationalTrace : Trace {
    fun start(startCheckpoint: Checkpoint)
    fun addCheckpoint(intermediateCheckpoint: Checkpoint)
    fun stop(stopCheckpoint: Checkpoint)
    fun cancel(cancelCheckpoint: Checkpoint)
}