package io.polyakov.tracy.dispatcher

import io.polyakov.tracy.model.Checkpoint
import io.polyakov.tracy.model.OperationalTrace

internal interface TraceDispatcher {
    fun dispatchStart(trace: OperationalTrace, startCheckpoint: Checkpoint)
    fun dispatchCancel(trace: OperationalTrace, cancelCheckpoint: Checkpoint)
    fun dispatchMediate(trace: OperationalTrace, intermediateCheckpoint: Checkpoint)
    fun dispatchStop(trace: OperationalTrace, stopCheckpoint: Checkpoint)
}
