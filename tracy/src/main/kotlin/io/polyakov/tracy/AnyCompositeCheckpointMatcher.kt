package io.polyakov.tracy

import io.polyakov.tracy.model.Checkpoint
import io.polyakov.tracy.model.CheckpointMatcher

internal class AnyCompositeCheckpointMatcher(
    private vararg val matchers: CheckpointMatcher
) : CheckpointMatcher {

    override fun matches(checkpoint: Checkpoint): Boolean {
        return matchers.any { matcher -> matcher.matches(checkpoint) }
    }
}