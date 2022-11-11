package com.bagas.bwamov.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bagas.bwamov.databinding.FragmentSettingBinding
import com.bagas.bwamov.utils.Preferences
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class SettingFragment : Fragment() {

    lateinit var preferences: Preferences

    private var _binding : FragmentSettingBinding? =null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(context!!)

        binding.ivNama.text = preferences.getValues("nama")
        binding.tvEmail.text = preferences.getValues("email")

        Glide.with(this)
            .load(preferences.getValues("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(binding.ivProfile)

    }


}