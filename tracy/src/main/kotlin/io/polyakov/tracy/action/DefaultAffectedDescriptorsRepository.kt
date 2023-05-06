package io.polyakov.tracy.action

import io.polyakov.tracy.descriptor.TraceDescriptorProvider
import io.polyakov.tracy.model.Checkpoint

internal class DefaultAffectedDescriptorsRepository(
    descriptorsProvider: TraceDescriptorProvider
) : AffectedDescriptorsRepository {

    private val descriptors = descriptorsProvider.provide()
    private val affectedDescriptorsByCheckpoint = mutableMapOf<Checkpoint, List<AffectedDescriptor>>()

    override fun getAffectedTraces(checkpoint: Checkpoint): List<AffectedDescriptor> {
        return affectedDescriptorsByCheckpoint.getOrPut(checkpoint) {
            identifyAffectedDescriptors(checkpoint)
        }
    }

    private fun identifyAffectedDescriptors(checkpoint: Checkpoint): List<AffectedDescriptor> {
        val affectedTraces = mutableListOf<AffectedDescriptor>()

        descriptors.forEach { d ->
            when {
                d.startMatcher.matches(checkpoint) -> {
                    affectedTraces += d to TraceAction.START
                }
                d.stopMatcher.matches(checkpoint) -> {
                    affectedTraces += d to TraceAction.STOP
                }
                d.cancelMatcher.matches(checkpoint) -> {
                    affectedTraces += d to TraceAction.CANCEL
                }
            }
        }

        return affectedTraces
    }
}
