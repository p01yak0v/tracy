package io.polyakov.sample.common.descriptor

import io.polyakov.sample.common.checkpoint.CancelCheckpoint
import io.polyakov.sample.common.checkpoint.StartCheckpoint
import io.polyakov.sample.common.checkpoint.StopCheckpoint
import io.polyakov.tracy.model.AttributeExtractor
import io.polyakov.tracy.model.CheckpointMatcher
import io.polyakov.tracy.model.TraceDescriptor
import io.polyakov.tracy.model.attr

class SampleTrace : TraceDescriptor {
    override val name: String = "sample-trace"

    override val startMatcher = CheckpointMatcher { it is StartCheckpoint }
    override val stopMatcher = CheckpointMatcher { it is StopCheckpoint }
    override val cancelMatcher = CheckpointMatcher { it is CancelCheckpoint }

    override val attributeExtractor: AttributeExtractor = AttributeExtractor {
        when (it) {
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