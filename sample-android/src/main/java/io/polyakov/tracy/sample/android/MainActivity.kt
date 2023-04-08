package io.polyakov.tracy.sample.android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import io.polyakov.sample.common.checkpoint.StartCheckpoint
import io.polyakov.sample.common.checkpoint.StopCheckpoint
import io.polyakov.sample.common.descriptor.SampleDescriptorProvider
import io.polyakov.tracy.Tracy
import io.polyakov.tracy.destination.LoggingDestination
import io.polyakov.tracy.android.firebase.FirebaseDestination
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private companion object {
        const val SAMPLE_SLEEP_INTERVAL_IN_MS = 1000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trackDefaultTrace()

        val newScreenButton = findViewById<Button>(R.id.press_me_button)
        newScreenButton.setOnClickListener {
            startActivity(
                Intent(this, NotReallyMainActivity::class.java)
            )
        }
    }

    private fun trackDefaultTrace() {
        Tracy.pass(StartCheckpoint)
        Thread.sleep(SAMPLE_SLEEP_INTERVAL_IN_MS)
        Tracy.pass(StopCheckpoint)
    }
}
