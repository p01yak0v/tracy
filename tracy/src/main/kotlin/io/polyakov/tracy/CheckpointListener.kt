package io.polyakov.tracy

import io.polyakov.tracy.model.Checkpoint

interface CheckpointListener {
    fun onCheckpoint(checkpoint: Checkpoint)
}
