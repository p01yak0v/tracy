package io.polyakov.sample.common.descriptor

import io.polyakov.tracy.model.TraceDescriptor
import io.polyakov.tracy.model.TraceDescriptorProvider

class SampleDescriptorProvider : TraceDescriptorProvider {
    override fun provide(): Set<TraceDescriptor> {
        return setOf(
            SampleTrace()
        )
    }
}
