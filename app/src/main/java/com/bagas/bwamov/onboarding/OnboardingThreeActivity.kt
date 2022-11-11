package com.bagas.bwamov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.bagas.bwamov.R
import com.bagas.bwamov.sign.SignInActivity

class OnboardingThreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_three)

        findViewById<Button>(R.id.btn_home_three).setOnClickListener {
//            hapus semua page yang sebelumnya
            finishAffinity()
//            pindah screen ke sign in
            var intent = Intent(this@OnboardingThreeActivity, SignInActivity::class.java)
            startActivity(intent)
        }

    }
}