package com.example.llegadasegura.grupo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.llegadasegura.databinding.ActivityGruposJoinBinding

class grupos_join : AppCompatActivity() {
    private lateinit var binding: ActivityGruposJoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGruposJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}