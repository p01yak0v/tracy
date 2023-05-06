package io.polyakov.tracy.sample.android.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.polyakov.tracy.sample.android.R

class SampleFragmentsActivity : AppCompatActivity(R.layout.not_really_main_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, SampleFragment.create(R.color.purple_200))
            .setReorderingAllowed(true)
            .commitNow()
    }
}
