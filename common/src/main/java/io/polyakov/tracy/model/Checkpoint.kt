package io.polyakov.tracy.model

abstract class Checkpoint(val name: String) {
    val creationTimestamp = System.currentTimeMillis()

    override fun hashCode() = name.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Checkpoint) return false

        return name == other.name
    }
}
