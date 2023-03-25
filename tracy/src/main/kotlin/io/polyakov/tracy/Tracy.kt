package io.polyakov.tracy

import io.polyakov.tracy.destination.TraceDestination
import io.polyakov.tracy.destination.DestinationTraceStateDelegate
import io.polyakov.tracy.dispatcher.TraceDispatcherImpl
import io.polyakov.tracy.model.Checkpoint
import io.polyakov.tracy.model.TraceDescriptorProvider
import io.polyakov.tracy.model.TraceImpl
import io.polyakov.tracy.registry.TraceRegistryImpl

interface Tracy {
    fun pass(checkpoint: Checkpoint)

    companion object {

        private lateinit var tracy: Tracy

        fun init(provider: TraceDescriptorProvider, destinations: List<TraceDestination>) {
            tracy = TracyImpl(
                traceDescriptorProvider = provider,
                traceRegistry = TraceRegistryImpl(TraceImpl.Factory),
                traceDispatcher = TraceDispatcherImpl(
                    DestinationTraceStateDelegate(destinations)
                )
            )
        }

        fun pass(checkpoint: Checkpoint) = tracy.pass(checkpoint)
    }
}