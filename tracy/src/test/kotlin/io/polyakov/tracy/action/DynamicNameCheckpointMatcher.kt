package io.polyakov.tracy.action

import io.polyakov.tracy.model.Checkpoint
import io.polyakov.tracy.model.CheckpointMatcher

class DynamicNameCheckpointMatcher(var checkpointName: String) : CheckpointMatcher {

    override fun matches(checkpoint: Checkpoint): Boolean {
        return checkpoint.name == checkpointName
    }
}
