package io.polyakov.tracy.matcher

import io.polyakov.tracy.model.Checkpoint

class AnyCompositeCheckpointMatcher(
    private vararg val matchers: CheckpointMatcher
) : CheckpointMatcher {

    init {
        require(matchers.isNotEmpty()) {
            "No matchers passed for AnyCompositeCheckpointMatcher. At least one matcher is required."
        }
    }

    override fun matches(checkpoint: Checkpoint): Boolean {
        return matchers.any { it.matches(checkpoint) }
    }
}
