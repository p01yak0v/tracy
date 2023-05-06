package io.polyakov.tracy.sample.android.descriptor

import io.polyakov.tracy.android.checkpoint.ActivityCheckpoint.ActivityState
import io.polyakov.tracy.android.checkpoint.BackgroundCheckpoint
import io.polyakov.tracy.android.checkpoint.FragmentCheckpoint.FragmentState
import io.polyakov.tracy.android.matcher.activityState
import io.polyakov.tracy.android.matcher.fragmentState
import io.polyakov.tracy.matcher.CheckpointMatcher
import io.polyakov.tracy.descriptor.TraceDescriptor
import io.polyakov.tracy.sample.android.MainActivity
import io.polyakov.tracy.sample.android.fragment.SampleFragment

class ActivityToFragmentTraceDescriptor : TraceDescriptor {
    override val name: String = "main-activity-to-sample-fragment-destroyed"
    override val startMatcher by activityState<MainActivity>(ActivityState.STARTED)
    override val stopMatcher by fragmentState<SampleFragment>(FragmentState.DESTROYED)
    override val cancelMatcher = CheckpointMatcher { it is BackgroundCheckpoint}
}
