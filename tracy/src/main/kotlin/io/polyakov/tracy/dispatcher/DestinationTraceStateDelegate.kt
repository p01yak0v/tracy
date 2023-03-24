package io.polyakov.tracy.dispatcher

import io.polyakov.tracy.annotation.ExcludeDestination
import io.polyakov.tracy.destination.TraceDestination
import io.polyakov.tracy.model.Trace
import io.polyakov.tracy.model.Trace.State

internal class DestinationTraceStateDelegate(
    private val destinations: List<TraceDestination>
) : TraceStateDelegate {

    override fun onTraceStateChanged(trace: Trace, state: State) {
        when (state) {
            State.STARTED ->  processTraceStart(trace)
            State.STOPPED -> processTraceStop(trace)
            State.CANCELLED -> processTraceCancelled(trace)
            else -> println("Trace state is not processed: ${state.name}")
        }
    }

    private fun processTraceStart(trace: Trace) {
        destinations.forEach {
            if (isDestinationExcluded(trace, it)) {
                return@forEach
            }

            it.start(trace)
        }
    }

    private fun processTraceStop(trace: Trace) {
        destinations.forEach {
            if (isDestinationExcluded(trace, it)) {
                return@forEach
            }

            fillAttributes(trace, it)

            it.stop(trace)
        }
    }

    private fun processTraceCancelled(trace: Trace) {
        destinations.forEach {
            if (isDestinationExcluded(trace, it)) {
                return@forEach
            }

            it.cancel(trace)
        }
    }

    // TODO : optimize this, it takes a while to complete, move to prepared repo
    private fun isDestinationExcluded(trace: Trace, destination: TraceDestination): Boolean {
        val excludeAnnotation = trace.descriptor::class
            .annotations
            .firstOrNull { it is ExcludeDestination } as ExcludeDestination?

        val excludedDestinations = excludeAnnotation?.destinations.orEmpty()

        return excludedDestinations.contains(destination::class)
    }

    private fun fillAttributes(trace: Trace, destination: TraceDestination) {
        val attributes = trace.checkpoints
            .flatMap(trace.descriptor.attributeExtractor::extract)

        destination.fillAttributes(trace, attributes)
    }
}