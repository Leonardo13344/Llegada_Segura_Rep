package com.example.llegadasegura.principal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.llegadasegura.R
import com.example.llegadasegura.databinding.FragmentDirectionsBinding


class DirectionsFragment : Fragment() {

    private lateinit var binding: FragmentDirectionsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDirectionsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}