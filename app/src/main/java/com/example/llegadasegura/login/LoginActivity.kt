package com.example.llegadasegura.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.llegadasegura.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var correo: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        correo = binding.editTextCorreo
        setContentView(binding.root)
        binding.btnContinuar.setOnClickListener{
            nextScreen(correo.text.toString())
        }
    }

    private fun nextScreen(correo:String){
        val intent = Intent(this, Login2Activity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("correo", correo)
        startActivity(intent)
    }


}