package com.bagas.bwamov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.bagas.bwamov.R
import com.bagas.bwamov.sign.SignInActivity
import com.bagas.bwamov.sign.SignUpActivity
import com.bagas.bwamov.utils.Preferences

class OnboardingOneActivity : AppCompatActivity() {

    lateinit var preference : Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)

        preference = Preferences(this)

        if (preference.getValues("onboarding").equals("1")) {
            finishAffinity()
            var intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }

//        membuat listener untuk ke onboarding ke 2
        findViewById<Button>(R.id.btn_home).setOnClickListener {
            var intent = Intent(this@OnboardingOneActivity, OnboardingTwoActivity::class.java)
            startActivity(intent)
        }

//        membuat Listener untuk ke daftar
        findViewById<Button>(R.id.btn_daftar).setOnClickListener {
            preference.setvalues("onboarding", "1")
            finishAffinity()
            var intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }

    }
}