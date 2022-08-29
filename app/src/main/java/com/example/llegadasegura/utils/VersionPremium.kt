package com.example.llegadasegura.utils

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.llegadasegura.databinding.VersionPremiumBinding
import com.example.llegadasegura.principal.PrincipalActivity

class VersionPremium: AppCompatActivity() {

    private lateinit var binding: VersionPremiumBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VersionPremiumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegresar.setOnClickListener{
            IrAActividadPrincipal()
        }
    }
    fun IrAActividadPrincipal(){
        finish()
    }
}