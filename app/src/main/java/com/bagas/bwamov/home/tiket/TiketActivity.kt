package com.bagas.bwamov.home.tiket

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bagas.bwamov.R
import com.bagas.bwamov.model.Checkout
import com.bagas.bwamov.model.Film
import com.bumptech.glide.Glide

class TiketActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiket)

        var data = intent.getParcelableExtra<Film>("data")
        findViewById<TextView>(R.id.tv_title).text = data!!.judul
        findViewById<TextView>(R.id.tv_genre).text = data!!.genre
        findViewById<TextView>(R.id.tv_rate).text = data!!.rating

        Glide.with(this)
            .load(data.poster)
            .into(findViewById<ImageView>(R.id.iv_poster_image))

        findViewById<RecyclerView>(R.id.rc_checkout).layoutManager = LinearLayoutManager(this)
        dataList.add(Checkout("C1", ""))
        dataList.add(Checkout("C2", ""))

        findViewById<RecyclerView>(R.id.rc_checkout).adapter = TiketAdapter(dataList){

        }

        findViewById<ImageView>(R.id.iv_close).setOnClickListener {
            finish()
        }

        findViewById<ImageView>(R.id.iv_barcode).setOnClickListener {
            showDialog("Silahkan melakukan scanning pada counter tiket terdekat")
        }
    }

    private fun showDialog(title: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_qr)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        val tvDesc = dialog.findViewById(R.id.tv_desc) as TextView
        tvDesc.text = title

        val btnClose = dialog.findViewById(R.id.btn_close) as Button
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}