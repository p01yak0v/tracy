package io.polyakov.tracy

import io.polyakov.tracy.action.DefaultAffectedDescriptorsRepository
import io.polyakov.tracy.descriptor.TraceDescriptorProvider
import io.polyakov.tracy.destination.DestinationTraceStateDelegate
import io.polyakov.tracy.destination.TraceDestination
import io.polyakov.tracy.dispatcher.TraceDispatcherImpl
import io.polyakov.tracy.model.Checkpoint
import io.polyakov.tracy.model.TraceImpl
import io.polyakov.tracy.registry.TraceRegistryImpl

interface Tracy {
    fun pass(checkpoint: Checkpoint)

    companion object {

        private lateinit var instance: Tracy

        fun init(provider: TraceDescriptorProvider, destinations: List<TraceDestination>) {
            instance = TracyImpl(
                affectedDescriptorsRepository = DefaultAffectedDescriptorsRepository(provider),
                traceRegistry = TraceRegistryImpl(TraceImpl.Factory),
                traceDispatcher = TraceDispatcherImpl(
                    DestinationTraceStateDelegate(destinations)
                )
            )
        }

        fun pass(checkpoint: Checkpoint) = instance.pass(checkpoint)
    }
}
