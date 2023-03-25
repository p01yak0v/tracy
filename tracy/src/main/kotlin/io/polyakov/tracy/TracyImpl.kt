package io.polyakov.tracy

import io.polyakov.tracy.action.AffectedDescriptorsRepository
import io.polyakov.tracy.action.TraceAction
import io.polyakov.tracy.dispatcher.TraceDispatcher
import io.polyakov.tracy.model.Checkpoint
import io.polyakov.tracy.model.TraceDescriptor
import io.polyakov.tracy.model.TraceDescriptorProvider
import io.polyakov.tracy.registry.TraceRegistry

internal class TracyImpl(
    private val affectedDescriptorsRepository: AffectedDescriptorsRepository,
    private val traceRegistry: TraceRegistry,
    private val traceDispatcher: TraceDispatcher
) : Tracy {

    override fun pass(checkpoint: Checkpoint) {
        val affectedTraces = affectedDescriptorsRepository.getAffectedTraces(checkpoint)
        for (trace in affectedTraces) {
            val (descriptor, action) = trace

            when (action) {
                TraceAction.START -> startTrace(descriptor, checkpoint)
                TraceAction.STOP -> stopTrace(descriptor, checkpoint)
                TraceAction.CANCEL -> cancelTrace(descriptor, checkpoint)
            }
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
}