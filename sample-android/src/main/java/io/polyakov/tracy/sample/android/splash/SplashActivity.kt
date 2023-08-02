package io.polyakov.tracy.sample.android.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import io.polyakov.tracy.Tracy
import io.polyakov.tracy.sample.android.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Tracy.pass(SplashStartedCheckpoint)
        installSplashScreen().setOnExitAnimationListener {
            Tracy.pass(SplashFinishedCheckpoint)

            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)

            finish()
        }
    }
}
