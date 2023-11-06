package io.polyakov.tracy.sample.jvm

import io.polyakov.sample.common.checkpoint.StartCheckpoint
import io.polyakov.sample.common.checkpoint.StopCheckpoint
import io.polyakov.sample.common.descriptor.SampleDescriptorProvider
import io.polyakov.tracy.Tracy
import io.polyakov.tracy.destination.LoggingDestination

private const val SAMPLE_SLEEP_INTERVAL_IN_MS = 1000L

fun main(args: Array<String>) {
    Tracy.init {
        traceProvider { SampleDescriptorProvider() }
        destinations {
            setOf(
                LoggingDestination("LoggingJvm#1"),
                LoggingDestination("LoggingJvm#2")
            )
        }
    }

    Tracy.pass(StartCheckpoint)

    Thread.sleep(SAMPLE_SLEEP_INTERVAL_IN_MS)

    Tracy.pass(StopCheckpoint)
}
