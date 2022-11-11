package com.bagas.bwamov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bagas.bwamov.onboarding.OnboardingOneActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        handle splash screen 5 detik
        var handle = Handler()
        handle.postDelayed({
//            perpindah halaman dari splash ke onboariding 1
            var intent = Intent(this@SplashScreenActivity, OnboardingOneActivity::class.java)
            startActivity(intent)
//            hapus cache agar tidak balik ke splash
            finish()
        }, 5000)

    }
}