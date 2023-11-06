package io.polyakov.tracy

import io.polyakov.tracy.action.AffectedDescriptorsRepository
import io.polyakov.tracy.action.TraceAction
import io.polyakov.tracy.descriptor.TraceDescriptor
import io.polyakov.tracy.dispatcher.TraceDispatcher
import io.polyakov.tracy.model.Checkpoint
import io.polyakov.tracy.registry.TraceRegistry

internal class TracyImpl(
    private val affectedDescriptorsRepository: AffectedDescriptorsRepository,
    private val traceRegistry: TraceRegistry,
    private val traceDispatcher: TraceDispatcher
) : Tracy {

    private val checkpointListeners = mutableSetOf<CheckpointListener>()

    override fun addCheckpointListener(checkpointListener: CheckpointListener) {
        checkpointListeners += checkpointListener
    }

    override fun removeCheckpointListener(checkpointListener: CheckpointListener) {
        checkpointListeners -= checkpointListener
    }

    override fun pass(checkpoint: Checkpoint) {
        checkpointListeners.forEach { it.onCheckpoint(checkpoint) }

        val affectedTraces = affectedDescriptorsRepository.getAffectedTraces(checkpoint)
        for (trace in affectedTraces) {
            val (descriptor, action) = trace

            val actionHandler = when (action) {
                TraceAction.START -> ::startTrace
                TraceAction.STOP -> ::stopTrace
                TraceAction.CANCEL -> ::cancelTrace
                TraceAction.ENRICH -> ::enrichTrace
            }

            actionHandler(descriptor, checkpoint)
        }
    }

    private fun startTrace(descriptor: TraceDescriptor, initialCheckpoint: Checkpoint) {
        val trace = traceRegistry.createTrace(descriptor)
        if (trace == null) {
            // TODO : startTrace is called twice for one trace; handle
            return
        }

        traceDispatcher.dispatchStart(trace, initialCheckpoint)
    }

    private fun stopTrace(descriptor: TraceDescriptor, stopCheckpoint: Checkpoint) {
        val trace = traceRegistry.removeTrace(descriptor)
        if (trace == null) {
            // TODO : stopTrace is called w/o startTrace; handle
            return
        }

        traceDispatcher.dispatchStop(trace, stopCheckpoint)
    }

    private fun cancelTrace(descriptor: TraceDescriptor, cancelCheckpoint: Checkpoint) {
        val trace = traceRegistry.removeTrace(descriptor)
        if (trace == null) {
            // TODO : cancelTrace is called w/o startTrace; handle
            return
        }

        traceDispatcher.dispatchCancel(trace, cancelCheckpoint)
    }

    private fun enrichTrace(descriptor: TraceDescriptor, enrichmentCheckpoint: Checkpoint) {
        val trace = traceRegistry.getTrace(descriptor)
        if (trace == null) {
            // TODO : enriching already destroyed or not yet created trace
            return
        }

        traceDispatcher.dispatchEnrichment(trace, enrichmentCheckpoint)
    }
}
