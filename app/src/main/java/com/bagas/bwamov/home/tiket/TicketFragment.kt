package com.bagas.bwamov.home.tiket

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagas.bwamov.databinding.FragmentTicketBinding
import com.bagas.bwamov.home.dashboard.ComingSoonAdapter
import com.bagas.bwamov.model.Film
import com.bagas.bwamov.utils.Preferences
import com.google.firebase.database.*


class TicketFragment : Fragment() {

    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Film>()

    private var _binding: FragmentTicketBinding? =null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(context!!)
        mDatabase = FirebaseDatabase.getInstance("https://bwa-mov-731a2-default-rtdb.firebaseio.com/").getReference("Film")

        binding.rcTiket.layoutManager = LinearLayoutManager(context)
        getData()

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapshot.children){
                    val film = getdataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                binding.rcTiket.adapter = ComingSoonAdapter(dataList) {
                    val intent = Intent(context, TiketActivity::class.java).putExtra("data",it)
                    startActivity(intent)
                }

                binding.tvTotal.setText("${dataList.size} Movies")

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }


}