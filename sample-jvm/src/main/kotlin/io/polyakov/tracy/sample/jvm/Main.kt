package io.polyakov.tracy.sample.jvm

import io.polyakov.tracy.Tracy
import io.polyakov.tracy.model.*

fun main(args: Array<String>) {
    Tracy.init(
        object : TraceDescriptorProvider {
            override fun provide(): Set<TraceDescriptor> {
                return setOf(
                    SampleTrace()
                )
            }
        }
    )

    Tracy.pass(StartCheckpoint)

    Thread.sleep(1000)

    Tracy.pass(StopCheckpoint)
}

object StartCheckpoint : Checkpoint("sample-start-checkpoint")
object StopCheckpoint : Checkpoint("sample-stop-checkpoint")
object CancelCheckpoint : Checkpoint("sample-cancel-checkpoint")

class SampleTrace : TraceDescriptor {
    override val name: String = "sample-trace"

    override val startMatcher = CheckpointMatcher { it is StartCheckpoint }
    override val stopMatcher = CheckpointMatcher { it is StopCheckpoint }
    override val cancelMatcher = CheckpointMatcher { it is CancelCheckpoint }

    override val attributeExtractor: AttributeExtractor = AttributeExtractor {
        when(it) {
            is StartCheckpoint -> {
                listOf("start-attribute" attr it.name)
            }
            is StopCheckpoint -> {
                listOf("stop-attribute" attr it.creationTimestamp)
            }
            else -> emptyList()
        }
    }
}