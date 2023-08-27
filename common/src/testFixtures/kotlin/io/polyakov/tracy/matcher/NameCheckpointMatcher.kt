package io.polyakov.tracy.matcher

import io.polyakov.tracy.model.Checkpoint

class NameCheckpointMatcher(var checkpointName: String) : CheckpointMatcher {

    override fun matches(checkpoint: Checkpoint): Boolean {
        return checkpoint.name == checkpointName
    }
}
