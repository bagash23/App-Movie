package com.bagas.bwamov.home.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagas.bwamov.DetailActivity
import com.bagas.bwamov.R
import com.bagas.bwamov.databinding.FragmentDashboardBinding
import com.bagas.bwamov.model.Film
import com.bagas.bwamov.utils.Preferences
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import java.text.NumberFormat
import java.util.Locale


class DashboardFragment : Fragment() {

    private lateinit var preferences: Preferences
    private lateinit var mDatabse: DatabaseReference

    private var _binding: FragmentDashboardBinding? =null
    private val binding get() = _binding!!


//    model
    private var dataList = ArrayList<Film>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_dashboard, container, false)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(activity!!.applicationContext)
        mDatabse = FirebaseDatabase.getInstance("https://bwa-mov-731a2-default-rtdb.firebaseio.com/").getReference("Film")
//        manggil data nama
        binding.ivNamaDashboard.setText(preferences.getValues("nama"))
//        memanggil saldo dengan menkonfer ke rupiah
        if (!preferences.getValues("saldo").equals("")) {
            currency(preferences.getValues("saldo")!!.toDouble(), binding.ivSaldoDashboard)
        }

//        memanggil poto menggunakan preferences
        Glide.with(this)
            .load(preferences.getValues("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(binding.ivProfileDashboard)

        Log.v("tamvan", "url "+preferences.getValues("url"))

//        pemanggilan recyle view
        binding.rvNowPlaying.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvComingSoon.layoutManager = LinearLayoutManager(context)

//        membuat function get data untuk meamnggil data dari firebase
        getData()

    }

    private fun getData() {
        mDatabse.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                agar data tidak duplikat
                dataList.clear()
                for (getdataSanpshot in dataSnapshot.children) {
                    var film = getdataSanpshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

//                buat adapter di masing" recyle
                binding.rvNowPlaying.adapter = NowPlayingAdapter(dataList) {
                    var intent = Intent(context, DetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }
                binding.rvComingSoon.adapter = ComingSoonAdapter(dataList) {
                    var intent = Intent(context, DetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    //    konfert ke format duit
    private fun currency(harga : Double, textView: TextView) {
        val localID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localID)
        textView.setText(formatRupiah.format(harga))
    }


}