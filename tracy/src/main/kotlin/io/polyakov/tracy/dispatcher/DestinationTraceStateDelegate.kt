package io.polyakov.tracy.dispatcher

import io.polyakov.tracy.annotation.ExcludeDestination
import io.polyakov.tracy.destination.TraceDestination
import io.polyakov.tracy.model.Trace

internal class DestinationTraceStateDelegate(
    private val destinations: List<TraceDestination>
) : TraceStateDelegate {

    override fun onTraceStarted(trace: Trace) {
        destinations.forEach {
            if (isDestinationExcluded(trace, it)) {
                return@forEach
            }

            it.start(trace)
        }
    }

    override fun onTraceStopped(trace: Trace) {
        destinations.forEach {
            if (isDestinationExcluded(trace, it)) {
                return@forEach
            }

            fillAttributes(trace, it)

            it.stop(trace)
        }
    }

    override fun onTraceCancelled(trace: Trace) {
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