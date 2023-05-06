package io.polyakov.tracy.sample.android

import io.polyakov.tracy.descriptor.TraceDescriptor
import io.polyakov.tracy.descriptor.TraceDescriptorProvider
import io.polyakov.tracy.sample.android.descriptor.ActivityToFragmentTraceDescriptor
import io.polyakov.tracy.sample.android.descriptor.CrossActivityTraceDescriptor

class SampleAndroidDescriptorProvider(
    private val decorated: TraceDescriptorProvider
) : TraceDescriptorProvider {

    override fun provide(): Set<TraceDescriptor> {
        return mutableSetOf(
            CrossActivityTraceDescriptor(),
            ActivityToFragmentTraceDescriptor()
        ).apply {
            addAll(decorated.provide())
        }

    }
}
