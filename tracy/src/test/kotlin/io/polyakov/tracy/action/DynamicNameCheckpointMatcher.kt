package io.polyakov.tracy.action

import io.polyakov.tracy.matcher.CheckpointMatcher
import io.polyakov.tracy.model.Checkpoint

class DynamicNameCheckpointMatcher(var checkpointName: String) : CheckpointMatcher {

    override fun matches(checkpoint: Checkpoint): Boolean {
        return checkpoint.name == checkpointName
    }
}
