package com.bagas.bwamov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bagas.bwamov.R
import com.bagas.bwamov.model.Checkout
import com.bagas.bwamov.model.Film

class PilihBangkuActivity : AppCompatActivity() {

//    status kursi pick and non pick
    var statusA3: Boolean = false
    var statusA4: Boolean = false
    var total:Int = 0

//    tampung semua data
    private var dataList = ArrayList<Checkout>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_bangku)

        var data = intent.getParcelableExtra<Film>("data")
        findViewById<TextView>(R.id.tv_kursi).text = data!!.judul


//cek apakah sudah ke select atau belum kursinya

        findViewById<ImageView>(R.id.a3).setOnClickListener {
            if (statusA3) {
                findViewById<ImageView>(R.id.a3).setImageResource(R.drawable.ic_rectangle_empty)
                statusA3 = false
                total -= 1
                beliTiket(total)
            } else {
                findViewById<ImageView>(R.id.a3).setImageResource(R.drawable.ic_rectangle_selected)
                statusA3 = true
                total += 1
                beliTiket(total)

                val data = Checkout("A3", "15000")
                dataList.add(data)

            }
        }

        findViewById<ImageView>(R.id.a4).setOnClickListener {
            if (statusA4) {
                findViewById<ImageView>(R.id.a4).setImageResource(R.drawable.ic_rectangle_empty)
                statusA4 = false
                total -= 1
                beliTiket(total)
            } else {
                findViewById<ImageView>(R.id.a4).setImageResource(R.drawable.ic_rectangle_selected)
                statusA4 = true
                total += 1
                beliTiket(total)

                val data = Checkout("A4", "15000")
                dataList.add(data)

            }
        }

        findViewById<Button>(R.id.btn_home).setOnClickListener {
            var intent = Intent(this, CheckoutActivity::class.java).putExtra("data", dataList)
            startActivity(intent)
        }

    }

    private fun beliTiket(total: Int) {
//        cek apakah sudah beli tiket atau belum
        if (total == 0) {
            findViewById<Button>(R.id.btn_home).setText("Beli Tiket")
            findViewById<Button>(R.id.btn_home).visibility = View.INVISIBLE
        } else {
            findViewById<Button>(R.id.btn_home).setText("Beli Tiket (" + total + ")")
            findViewById<Button>(R.id.btn_home).visibility = View.VISIBLE
        }
    }
}