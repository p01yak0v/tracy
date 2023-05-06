package io.polyakov.tracy.model.stub

import io.polyakov.tracy.matcher.CheckpointMatcher
import io.polyakov.tracy.descriptor.TraceDescriptor


open class StubTraceDescriptor(
    override val name: String = DESCRIPTOR_NAME
) : TraceDescriptor {

    internal companion object {
        const val DESCRIPTOR_NAME = "stub-trace-descriptor"
    }

    override val startMatcher = CheckpointMatcher { true }
    override val stopMatcher = CheckpointMatcher { true }
    override val cancelMatcher = CheckpointMatcher { true }
}
