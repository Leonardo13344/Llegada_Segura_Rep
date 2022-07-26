package com.example.llegadasegura.grupo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.llegadasegura.databinding.ActivityGruposBinding

class grupos : AppCompatActivity() {
    private lateinit var binding: ActivityGruposBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGruposBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}