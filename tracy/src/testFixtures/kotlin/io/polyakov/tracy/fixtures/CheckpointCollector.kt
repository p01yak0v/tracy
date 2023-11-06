package io.polyakov.tracy.fixtures

import io.polyakov.tracy.CheckpointListener
import io.polyakov.tracy.model.Checkpoint

class CheckpointCollector : CheckpointListener {

    private val _checkpoints = mutableListOf<Checkpoint>()
    val checkpoints: List<Checkpoint> = _checkpoints

    override fun onCheckpoint(checkpoint: Checkpoint) {
        _checkpoints.add(checkpoint)
    }

    fun assert(block: Assertions.() -> Unit) {
        Assertions().block()
    }

    inner class Assertions internal constructor() {

        inline fun <reified T : Checkpoint> has(matcher: T.() -> Boolean = { true }) {
            val targetJavaClass = T::class.java
            val targetCheckpoints: List<T> = checkpoints.filterIsInstance<T>()

            assert(targetCheckpoints.isNotEmpty()) { "${targetJavaClass.simpleName} not found" }

            assert(targetCheckpoints.any(matcher)) {
                "${targetJavaClass.simpleName} found, but doesn't match with matcher"
            }
        }

        inline fun <reified T : Checkpoint> hasNo(matcher: T.() -> Boolean = { true }) {
            val targetJavaClass = T::class.java
            val targetCheckpoints: List<T> = checkpoints.filterIsInstance<T>()

            assert(targetCheckpoints.isEmpty() || targetCheckpoints.none(matcher)) {
                "${targetJavaClass.simpleName} is not expected but found"
            }
        }
    }
}