package com.example.llegadasegura.principal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.llegadasegura.R
import com.example.llegadasegura.databinding.FragmentConfigurationBinding


class ConfigurationFragment : Fragment() {

    private lateinit var binding: FragmentConfigurationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConfigurationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}