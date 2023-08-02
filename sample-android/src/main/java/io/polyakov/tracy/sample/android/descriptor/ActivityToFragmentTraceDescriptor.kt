package io.polyakov.tracy.sample.android.descriptor

import io.polyakov.tracy.android.checkpoint.ActivityCheckpoint.ActivityState
import io.polyakov.tracy.android.checkpoint.BackgroundCheckpoint
import io.polyakov.tracy.android.checkpoint.FragmentCheckpoint.FragmentState
import io.polyakov.tracy.android.matcher.activityState
import io.polyakov.tracy.android.matcher.fragmentState
import io.polyakov.tracy.descriptor.TraceDescriptor
import io.polyakov.tracy.matcher.delegate.classMatcher
import io.polyakov.tracy.sample.android.fragment.FirstFragment
import io.polyakov.tracy.sample.android.main.MainActivity

class ActivityToFragmentTraceDescriptor : TraceDescriptor {
    override val name = "main-activity-to-first-fragment-resumed"

    override val startMatcher by activityState<MainActivity>(ActivityState.STARTED)
    override val stopMatcher by fragmentState<FirstFragment>(FragmentState.RESUMED)
    override val cancelMatcher by classMatcher<BackgroundCheckpoint>()
}
