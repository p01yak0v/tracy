package io.polyakov.tracy.matcher

import io.polyakov.tracy.model.Checkpoint

class AllCompositeCheckpointMatcher(
    private vararg val matchers: CheckpointMatcher
) : CheckpointMatcher {

    init {
        require(matchers.isNotEmpty()) {
            "No matchers passed for AllCompositeCheckpointMatcher. At least one matcher is required."
        }
    }

    override fun matches(checkpoint: Checkpoint): Boolean {
        return matchers.all { it.matches(checkpoint) }
    }
}
