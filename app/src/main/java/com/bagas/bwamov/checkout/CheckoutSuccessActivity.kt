package com.bagas.bwamov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.bagas.bwamov.R
import com.bagas.bwamov.home.HomeActivity

class CheckoutSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_success)

        findViewById<Button>(R.id.btn_home).setOnClickListener {
            finishAffinity()

            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }
}