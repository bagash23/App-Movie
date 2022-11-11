package com.bagas.bwamov

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bagas.bwamov.checkout.PilihBangkuActivity
import com.bagas.bwamov.home.dashboard.PlaysAdapter
import com.bagas.bwamov.model.Film
import com.bagas.bwamov.model.Plays
import com.bumptech.glide.Glide
import com.google.firebase.database.*

class DetailActivity : AppCompatActivity() {

    private lateinit var mdatabase : DatabaseReference
    private var dataList = ArrayList<Plays>()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


//        ambil parms dari dashboard
        val data = intent.getParcelableExtra<Film>("data")
        mdatabase = FirebaseDatabase.getInstance("https://bwa-mov-731a2-default-rtdb.firebaseio.com/").getReference("Film")
            .child(data?.judul.toString())
            .child("play")



        findViewById<TextView>(R.id.tv_kursi).text = data!!.judul
        findViewById<TextView>(R.id.tv_genre).text = data!!.genre
        findViewById<TextView>(R.id.tv_desc).text = data!!.desc
        findViewById<TextView>(R.id.tv_rate).text = data!!.rating


        Glide.with(this)
            .load(data.poster)
            .into(findViewById(R.id.tv_poster))

        findViewById<RecyclerView>(R.id.rv_who_play).layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getData()

        findViewById<Button>(R.id.btn_pilih_bangku).setOnClickListener {
            var intent = Intent(this@DetailActivity, PilihBangkuActivity::class.java).putExtra("data", data)
            startActivity(intent)
        }

    }

    private fun getData() {
        mdatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapshot.children){
                    var Film = getdataSnapshot.getValue(Plays::class.java)
                    dataList.add(Film!!)
                }

//                tampilin ke adapter
                findViewById<RecyclerView>(R.id.rv_who_play).adapter = PlaysAdapter(dataList) {

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@DetailActivity, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}