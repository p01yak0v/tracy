package io.polyakov.tracy.matcher

import io.polyakov.tracy.model.Checkpoint

class AnyCompositeCheckpointMatcher(
    private vararg val matchers: CheckpointMatcher
) : CheckpointMatcher {

    override fun matches(checkpoint: Checkpoint): Boolean {
        return matchers.any { it.matches(checkpoint) }
    }
}
