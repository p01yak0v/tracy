package io.polyakov.sample.common.descriptor

import io.polyakov.sample.common.checkpoint.CancelCheckpoint
import io.polyakov.sample.common.checkpoint.StartCheckpoint
import io.polyakov.sample.common.checkpoint.StopCheckpoint
import io.polyakov.tracy.attribute.AttributeExtractor
import io.polyakov.tracy.attribute.attr
import io.polyakov.tracy.descriptor.TraceDescriptor
import io.polyakov.tracy.matcher.delegate.classMatcher

class SampleTrace : TraceDescriptor {
    override val name: String = "sample-trace"

    override val startMatcher by classMatcher<StartCheckpoint>()
    override val stopMatcher by classMatcher<StopCheckpoint>()
    override val cancelMatcher by classMatcher<CancelCheckpoint>()

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
