package com.example.llegadasegura.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.llegadasegura.databinding.RecuperarContraseniaBinding
import com.example.llegadasegura.registro.MainActivity
import com.google.firebase.auth.FirebaseAuth

class RecuperarContrasenia : AppCompatActivity() {
        private lateinit var binding: RecuperarContraseniaBinding
        private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Entr","Esta entrando a la clase")
        super.onCreate(savedInstanceState)
        binding = RecuperarContraseniaBinding.inflate(layoutInflater)

        binding.btnRecuperarContrasenia.setOnClickListener{
            enviarCorreoRecuperacion(binding.editTextCorreoR.text.toString())
        }
        setContentView(binding.root)


    }
    private fun volverAInicio(){
        val intent = Intent(this, MainActivity::class.java )
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
    private fun enviarCorreoRecuperacion(correo: String){
            auth.setLanguageCode("es")
        auth.sendPasswordResetEmail(correo).addOnCompleteListener {task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Revise su correo", Toast.LENGTH_LONG).show()
                volverAInicio()
            } else {
                Toast.makeText(this, "No se pudo enviar el correo", Toast.LENGTH_LONG).show()
            }

        }
    }
}