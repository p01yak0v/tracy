package io.polyakov.tracy.sample.android

import io.polyakov.tracy.model.TraceDescriptor
import io.polyakov.tracy.model.TraceDescriptorProvider
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
