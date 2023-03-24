package io.polyakov.tracy.sample.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.polyakov.sample.common.checkpoint.StartCheckpoint
import io.polyakov.sample.common.checkpoint.StopCheckpoint
import io.polyakov.sample.common.descriptor.SampleDescriptorProvider
import io.polyakov.tracy.Tracy

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Tracy.init(SampleDescriptorProvider())

        Tracy.pass(StartCheckpoint)

        Thread.sleep(1000)

        Tracy.pass(StopCheckpoint)

    }
}