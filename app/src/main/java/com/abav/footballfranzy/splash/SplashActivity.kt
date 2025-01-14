package com.abav.footballfranzy.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.abav.footballfranzy.auth.AuthActivity
import com.abav.footballfranzy.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        navigateToAuth()
    }
// To navigate, it takes 3 seconds and after that we navigate to auth activity.
    private fun navigateToAuth() {
        // doing a task for a specific time frame/delay, here it is 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            // Navigate to MainActivity by sending us to AuthActivity
            startActivity(Intent(this, AuthActivity::class.java))
            finish() // Finish SplashActivity to remove it from the back stack
        }, 3000) // Delay for 3 seconds (3000ms)

    }
}