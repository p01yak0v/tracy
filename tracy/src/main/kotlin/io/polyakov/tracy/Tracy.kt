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

    fun addCheckpointListener(checkpointListener: CheckpointListener)

    fun removeCheckpointListener(checkpointListener: CheckpointListener)

    companion object {

        private lateinit var delegate: Tracy

        fun init(block: Builder.() -> Unit) {
            delegate = Builder().apply(block).build()
        }

        fun pass(checkpoint: Checkpoint) {
            delegate.pass(checkpoint)
        }

        fun addCheckpointListener(checkpointListener: CheckpointListener) {
            delegate.addCheckpointListener(checkpointListener)
        }

        fun removeCheckpointListener(checkpointListener: CheckpointListener) {
            delegate.removeCheckpointListener(checkpointListener)
        }
    }

    class Builder {

        private lateinit var traceDescriptorProvider: TraceDescriptorProvider
        private val destinations = mutableSetOf<TraceDestination>()

        fun traceProvider(provider: () -> TraceDescriptorProvider) {
            traceDescriptorProvider = provider()
        }

        fun destinations(provider: () -> Set<TraceDestination>) {
            destinations.addAll(provider())
        }

        fun build(): Tracy {
            val traceDescriptorProvider = requireNotNull(this.traceDescriptorProvider) {
                "Tracy is unable to operate without ${TraceDescriptorProvider::class.simpleName}"
            }

            return TracyImpl(
                affectedDescriptorsRepository = DefaultAffectedDescriptorsRepository(traceDescriptorProvider),
                traceRegistry = TraceRegistryImpl(TraceImpl.Factory),
                traceDispatcher = TraceDispatcherImpl(
                    DestinationTraceStateDelegate(destinations)
                )
            )
        }
    }
}
