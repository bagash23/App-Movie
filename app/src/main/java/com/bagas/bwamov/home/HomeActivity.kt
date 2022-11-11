package com.bagas.bwamov.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bagas.bwamov.R
import com.bagas.bwamov.home.dashboard.DashboardFragment
import com.bagas.bwamov.home.tiket.TicketFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentHome = DashboardFragment()
        val fragmentTicket = TicketFragment()
        val fragmentSetting = SettingFragment()

//        bottom tab
        findViewById<ImageView>(R.id.iv_menu1).setOnClickListener {
            setFragment(fragmentHome)
            changeImage(findViewById(R.id.iv_menu1), R.drawable.ic_home_active)
            changeImage(findViewById(R.id.iv_menu2), R.drawable.ic_tiket)
            changeImage(findViewById(R.id.iv_menu3), R.drawable.ic_profile)
        }
        findViewById<ImageView>(R.id.iv_menu2).setOnClickListener {
            setFragment(fragmentTicket)
            changeImage(findViewById(R.id.iv_menu1), R.drawable.ic_home)
            changeImage(findViewById(R.id.iv_menu2), R.drawable.ic_tiket_active)
            changeImage(findViewById(R.id.iv_menu3), R.drawable.ic_profile)
        }
        findViewById<ImageView>(R.id.iv_menu3).setOnClickListener {
            setFragment(fragmentSetting)
            changeImage(findViewById(R.id.iv_menu1), R.drawable.ic_home)
            changeImage(findViewById(R.id.iv_menu2), R.drawable.ic_tiket)
            changeImage(findViewById(R.id.iv_menu3), R.drawable.ic_profile_active)
        }

    }

//    load fragment
    private fun setFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmantTransaction = fragmentManager.beginTransaction()
        fragmantTransaction.replace(R.id.layout_frame, fragment)
        fragmantTransaction.commit()
    }

//    membuat fun merubah gambar
    private fun changeImage(imageView: ImageView, int: Int) {
        imageView.setImageResource(int)
    }

}