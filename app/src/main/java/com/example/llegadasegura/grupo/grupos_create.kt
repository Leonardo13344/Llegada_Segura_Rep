package com.example.llegadasegura.grupo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.llegadasegura.databinding.ActivityGruposCreateBinding

class grupos_create : AppCompatActivity() {
    private lateinit var binding: ActivityGruposCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGruposCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}