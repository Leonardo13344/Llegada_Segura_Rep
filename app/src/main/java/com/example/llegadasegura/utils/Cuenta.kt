package com.example.llegadasegura.utils

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.llegadasegura.databinding.ActivityTerminosResultBinding
import com.example.llegadasegura.databinding.CuentaConfiguracionBinding

class Cuenta: AppCompatActivity() {
    private lateinit var binding: CuentaConfiguracionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras
        super.onCreate(savedInstanceState)
        binding = CuentaConfiguracionBinding.inflate(layoutInflater)
        val telefono = bundle?.getString("telefono")
        val nombre = bundle?.getString("nombre")
        val apellido = bundle?.getString("apellido")
        binding.txtNombre.text = nombre
        binding.txtApellido.text = apellido
        binding.txtNumero.text = telefono
        setContentView(binding.root)
    }
}