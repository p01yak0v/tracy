package io.polyakov.sample.common.descriptor

import io.polyakov.tracy.descriptor.TraceDescriptor
import io.polyakov.tracy.descriptor.TraceDescriptorProvider

class SampleDescriptorProvider : TraceDescriptorProvider {
    override fun provide(): Set<TraceDescriptor> {
        return setOf(
            SampleTrace()
        )
    }
}
