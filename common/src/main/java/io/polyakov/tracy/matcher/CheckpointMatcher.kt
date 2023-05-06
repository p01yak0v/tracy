package io.polyakov.tracy.matcher

import io.polyakov.tracy.model.Checkpoint

fun interface CheckpointMatcher {
    fun matches(checkpoint: Checkpoint): Boolean
}
