package io.polyakov.tracy.sample.android.descriptor

import io.polyakov.tracy.descriptor.TraceDescriptor
import io.polyakov.tracy.matcher.delegate.classMatcher
import io.polyakov.tracy.matcher.delegate.none
import io.polyakov.tracy.sample.android.splash.SplashFinishedCheckpoint

class SplashRenderingTrace : TraceDescriptor {
    override val name = "splash-rendering"
    override val startMatcher by classMatcher<SplashFinishedCheckpoint>()
    override val stopMatcher by classMatcher<SplashFinishedCheckpoint>()
    override val cancelMatcher by none()
}
