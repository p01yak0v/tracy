package io.polyakov.tracy.action

import io.polyakov.tracy.model.stub.StubTraceDescriptor

internal class DynamicTraceDescriptor(name: String) : StubTraceDescriptor(name) {

    private companion object {
        const val START_CHECKPOINT_NAME = "start"
        const val STOP_CHECKPOINT_NAME = "stop"
        const val CANCEL_CHECKPOINT_NAME = "cancel"
    }

    val startCheckpointName = "$name-$START_CHECKPOINT_NAME"
    val stopCheckpointName = "$name-$STOP_CHECKPOINT_NAME"
    val cancelCheckpointName = "$name-$CANCEL_CHECKPOINT_NAME"

    override val startMatcher = DynamicNameCheckpointMatcher(startCheckpointName)
    override val stopMatcher = DynamicNameCheckpointMatcher(stopCheckpointName)
    override val cancelMatcher = DynamicNameCheckpointMatcher(cancelCheckpointName)
}
