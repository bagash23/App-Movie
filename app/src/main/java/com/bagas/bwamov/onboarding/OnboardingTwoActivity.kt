package com.bagas.bwamov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.bagas.bwamov.R

class OnboardingTwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_two)

        findViewById<Button>(R.id.btn_home_two).setOnClickListener {
            startActivity(Intent(this@OnboardingTwoActivity, OnboardingThreeActivity::class.java))
        }

    }
}