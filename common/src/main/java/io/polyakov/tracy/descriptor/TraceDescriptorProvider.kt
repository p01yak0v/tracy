package io.polyakov.tracy.descriptor

import io.polyakov.tracy.descriptor.TraceDescriptor

interface TraceDescriptorProvider {
    fun provide(): Set<TraceDescriptor>
}
