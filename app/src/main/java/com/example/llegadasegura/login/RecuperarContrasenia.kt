package com.example.llegadasegura.login

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.llegadasegura.databinding.RecuperarContraseniaBinding
import com.example.llegadasegura.registro.MainActivity
import com.google.firebase.auth.FirebaseAuth

class RecuperarContrasenia : AppCompatActivity() {
        private lateinit var binding: RecuperarContraseniaBinding
        private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = RecuperarContraseniaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRecuperarContrasenia.setOnClickListener{
            enviarCorreoRecuperacion(binding.editTextCorreoR.text.toString())
        }


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