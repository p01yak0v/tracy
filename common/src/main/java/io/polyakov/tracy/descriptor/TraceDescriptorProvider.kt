package io.polyakov.tracy.descriptor

interface TraceDescriptorProvider {
    fun provide(): Set<TraceDescriptor>
}
