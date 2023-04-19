package io.polyakov.tracy.model.stub

import io.polyakov.tracy.model.AttributeExtractor
import io.polyakov.tracy.model.CheckpointMatcher
import io.polyakov.tracy.model.TraceDescriptor


open class StubTraceDescriptor(
    override val name: String = DESCRIPTOR_NAME
) : TraceDescriptor {

    internal companion object {
        const val DESCRIPTOR_NAME = "stub-trace-descriptor"
    }

    override val startMatcher = CheckpointMatcher { true }
    override val stopMatcher = CheckpointMatcher { true }
    override val cancelMatcher = CheckpointMatcher { true }
    override val attributeExtractor = AttributeExtractor { emptyList() }
}
