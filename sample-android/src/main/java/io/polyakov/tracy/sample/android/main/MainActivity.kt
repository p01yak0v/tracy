package io.polyakov.tracy.sample.android.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.polyakov.sample.common.checkpoint.StartCheckpoint
import io.polyakov.sample.common.checkpoint.StopCheckpoint
import io.polyakov.tracy.Tracy
import io.polyakov.tracy.sample.android.R
import io.polyakov.tracy.sample.android.fragment.FragmentHostActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private companion object {
        const val SAMPLE_SLEEP_INTERVAL_IN_MS = 1000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trackDefaultTrace()

        val newScreenButton = findViewById<Button>(R.id.press_me_button)
        newScreenButton.setOnClickListener {
            Tracy.pass(PressMeCheckpoint)
            startActivity(
                Intent(this, FragmentHostActivity::class.java)
            )
        }
    }

    private fun trackDefaultTrace() {
        Tracy.pass(StartCheckpoint)
        Thread.sleep(SAMPLE_SLEEP_INTERVAL_IN_MS)
        Tracy.pass(StopCheckpoint)
    }
}
