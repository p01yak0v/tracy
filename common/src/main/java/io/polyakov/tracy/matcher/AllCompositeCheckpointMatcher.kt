package io.polyakov.tracy.matcher

import io.polyakov.tracy.model.Checkpoint

class AllCompositeCheckpointMatcher(
    private vararg val matchers: CheckpointMatcher
) : CheckpointMatcher {

    override fun matches(checkpoint: Checkpoint): Boolean {
        return matchers.all { it.matches(checkpoint) }
    }
}
