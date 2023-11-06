package io.polyakov.tracy.sample.android

import io.polyakov.tracy.descriptor.TraceDescriptor
import io.polyakov.tracy.descriptor.TraceDescriptorProvider
import io.polyakov.tracy.sample.android.descriptor.ActivityToFragmentTraceDescriptor
import io.polyakov.tracy.sample.android.descriptor.FirstFragmentToSecondFragmentTraceDescriptor
import io.polyakov.tracy.sample.android.descriptor.MainToFragmentHostTraceDescriptor
import io.polyakov.tracy.sample.android.descriptor.PressMeToThirdFragmentTraceDescriptor
import io.polyakov.tracy.sample.android.descriptor.SplashRenderingTrace

class SampleAndroidDescriptorProvider(
    private val decorated: TraceDescriptorProvider
) : TraceDescriptorProvider {

    override fun provide(): Set<TraceDescriptor> {
        return mutableSetOf(
            SplashRenderingTrace(),
            MainToFragmentHostTraceDescriptor(),
            ActivityToFragmentTraceDescriptor(),
            PressMeToThirdFragmentTraceDescriptor(),
            FirstFragmentToSecondFragmentTraceDescriptor()
        ).apply {
            addAll(decorated.provide())
        }
    }
}
