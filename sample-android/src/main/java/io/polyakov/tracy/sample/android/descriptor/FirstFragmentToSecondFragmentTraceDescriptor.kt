package io.polyakov.tracy.sample.android.descriptor

import io.polyakov.tracy.android.checkpoint.ActivityCheckpoint.ActivityState
import io.polyakov.tracy.android.checkpoint.FragmentCheckpoint.FragmentState
import io.polyakov.tracy.android.matcher.activityState
import io.polyakov.tracy.android.matcher.fragmentState
import io.polyakov.tracy.descriptor.TraceDescriptor
import io.polyakov.tracy.sample.android.fragment.FirstFragment
import io.polyakov.tracy.sample.android.fragment.FragmentHostActivity
import io.polyakov.tracy.sample.android.fragment.SecondFragment

class FirstFragmentToSecondFragmentTraceDescriptor : TraceDescriptor {
    override val name = "first-fragment-to-second-fragment"

    override val startMatcher by fragmentState<FirstFragment>(FragmentState.CREATED)
    override val stopMatcher by fragmentState<SecondFragment>(FragmentState.RESUMED)
    override val cancelMatcher by activityState<FragmentHostActivity>(ActivityState.DESTROYED)
}
