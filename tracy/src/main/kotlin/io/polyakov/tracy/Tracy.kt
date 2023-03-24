package io.polyakov.tracy

import io.polyakov.tracy.destination.LoggingDestination
import io.polyakov.tracy.dispatcher.DestinationTraceStateDelegate
import io.polyakov.tracy.dispatcher.TraceDispatcherImpl
import io.polyakov.tracy.model.Checkpoint
import io.polyakov.tracy.model.TraceDescriptorProvider
import io.polyakov.tracy.registry.TraceRegistryImpl

interface Tracy {
    fun pass(checkpoint: Checkpoint)

    companion object {

        private lateinit var tracy: Tracy

        fun init(provider: TraceDescriptorProvider) {
            tracy = TracyImpl(
                traceDescriptorProvider = provider,
                traceRegistry = TraceRegistryImpl(),
                traceDispatcher = TraceDispatcherImpl(
                    DestinationTraceStateDelegate(
                        listOf(
                            LoggingDestination("FirstDestination"),
                            LoggingDestination("SecondDestination")
                        )
                    )
                )
            )
        }

        fun pass(checkpoint: Checkpoint) = tracy.pass(checkpoint)
    }
}