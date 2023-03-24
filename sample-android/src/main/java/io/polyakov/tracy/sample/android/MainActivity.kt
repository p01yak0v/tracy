package io.polyakov.tracy.sample.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import io.polyakov.sample.common.checkpoint.StartCheckpoint
import io.polyakov.sample.common.checkpoint.StopCheckpoint
import io.polyakov.sample.common.descriptor.SampleDescriptorProvider
import io.polyakov.tracy.Tracy
import io.polyakov.tracy.destination.LoggingDestination
import io.polyakov.tracy.firebase.FirebaseDestination

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Tracy.init(
            SampleDescriptorProvider(),
            listOf(
                LoggingDestination("LoggingAndroid"),
                FirebaseDestination(Firebase.performance)
            )
        )

        Tracy.pass(StartCheckpoint)

        Thread.sleep(1000)

        Tracy.pass(StopCheckpoint)

    }
}