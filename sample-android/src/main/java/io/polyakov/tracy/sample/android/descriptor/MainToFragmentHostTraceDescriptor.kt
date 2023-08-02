package io.polyakov.tracy.sample.android.descriptor

import io.polyakov.tracy.android.checkpoint.ActivityCheckpoint.ActivityState
import io.polyakov.tracy.android.matcher.activityState
import io.polyakov.tracy.descriptor.TraceDescriptor
import io.polyakov.tracy.matcher.delegate.none
import io.polyakov.tracy.sample.android.fragment.FragmentHostActivity
import io.polyakov.tracy.sample.android.main.MainActivity

class MainToFragmentHostTraceDescriptor : TraceDescriptor {
    override val name = "main-activity-to-fragment-host-activity"

    override val startMatcher by activityState<MainActivity>(ActivityState.CREATED)
    override val stopMatcher by activityState<FragmentHostActivity>(ActivityState.RESUMED)
    override val cancelMatcher by none()
}
