package io.polyakov.tracy.sample.android.descriptor

import io.polyakov.tracy.android.checkpoint.ActivityCheckpoint.ActivityState
import io.polyakov.tracy.android.checkpoint.FragmentCheckpoint.FragmentState
import io.polyakov.tracy.android.matcher.activityState
import io.polyakov.tracy.android.matcher.fragmentState
import io.polyakov.tracy.descriptor.TraceDescriptor
import io.polyakov.tracy.matcher.delegate.classMatcher
import io.polyakov.tracy.sample.android.fragment.FragmentHostActivity
import io.polyakov.tracy.sample.android.fragment.ThirdFragment
import io.polyakov.tracy.sample.android.main.PressMeCheckpoint

class PressMeToThirdFragmentTraceDescriptor : TraceDescriptor {
    override val name = "press-me-to-third-activity-resumed"

    override val startMatcher by classMatcher<PressMeCheckpoint>()
    override val stopMatcher by fragmentState<ThirdFragment>(FragmentState.RESUMED)
    override val cancelMatcher by activityState<FragmentHostActivity>(ActivityState.DESTROYED)
}
