package io.polyakov.tracy.sample.jvm

import io.polyakov.sample.common.checkpoint.StartCheckpoint
import io.polyakov.sample.common.checkpoint.StopCheckpoint
import io.polyakov.sample.common.descriptor.SampleDescriptorProvider
import io.polyakov.tracy.Tracy
import io.polyakov.tracy.model.*

fun main(args: Array<String>) {
    Tracy.init(SampleDescriptorProvider())

    Tracy.pass(StartCheckpoint)

    Thread.sleep(1000)

    Tracy.pass(StopCheckpoint)
}