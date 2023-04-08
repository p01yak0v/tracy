package io.polyakov.tracy.model

fun interface CheckpointMatcher {
    fun matches(checkpoint: Checkpoint): Boolean
}
