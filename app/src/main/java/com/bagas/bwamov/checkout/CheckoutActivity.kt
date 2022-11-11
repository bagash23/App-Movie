package com.bagas.bwamov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bagas.bwamov.R
import com.bagas.bwamov.model.Checkout
import com.bagas.bwamov.utils.Preferences

class CheckoutActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()
    private var total:Int = 0
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Preferences(this)
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>

        for (a in dataList.indices) {
            total += dataList[a].harga!!.toInt()
        }

        dataList.add(Checkout("Total harus dibayar", total.toString()))

//        tampilkan data di recylenya
        findViewById<RecyclerView>(R.id.rc_checkout).layoutManager = LinearLayoutManager(this)
//        buat adapter recyle
        findViewById<RecyclerView>(R.id.rc_checkout).adapter = CheckoutAdapter(dataList) {

        }


        findViewById<Button>(R.id.btn_tiket).setOnClickListener {
            var intent = Intent(this, CheckoutSuccessActivity::class.java)
            startActivity(intent)
        }


    }
}