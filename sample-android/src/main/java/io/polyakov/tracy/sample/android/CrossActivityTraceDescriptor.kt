package io.polyakov.tracy.sample.android

import io.polyakov.tracy.android.checkpoint.ActivityCheckpoint.ActivityState
import io.polyakov.tracy.android.matcher.activityState
import io.polyakov.tracy.model.AttributeExtractor
import io.polyakov.tracy.model.CheckpointMatcher
import io.polyakov.tracy.model.TraceDescriptor

class CrossActivityTraceDescriptor : TraceDescriptor {
    override val startMatcher by activityState<MainActivity>(ActivityState.CREATED)
    override val stopMatcher by activityState<NotReallyMainActivity>(ActivityState.RESUMED)
    override val cancelMatcher = CheckpointMatcher { false }

    override val name: String
        get() = "main-create-to-not-really-main-resumed"

    override val attributeExtractor: AttributeExtractor
        get() = AttributeExtractor { emptyList() }
}