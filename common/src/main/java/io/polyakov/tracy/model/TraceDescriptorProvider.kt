package io.polyakov.tracy.model

interface TraceDescriptorProvider {
    fun provide(): Set<TraceDescriptor>
}