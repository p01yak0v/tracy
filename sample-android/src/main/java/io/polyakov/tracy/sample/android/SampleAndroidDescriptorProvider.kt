package io.polyakov.tracy.sample.android

import io.polyakov.tracy.model.TraceDescriptor
import io.polyakov.tracy.model.TraceDescriptorProvider

class SampleAndroidDescriptorProvider(
    private val decorated: TraceDescriptorProvider
) : TraceDescriptorProvider {

    override fun provide(): Set<TraceDescriptor> {
        return mutableSetOf<TraceDescriptor>(
            CrossActivityTraceDescriptor()
        ).apply {
            addAll(decorated.provide())
        }

    }
}
