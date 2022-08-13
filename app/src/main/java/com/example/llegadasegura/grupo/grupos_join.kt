package com.example.llegadasegura.grupo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.llegadasegura.databinding.ActivityGruposJoinBinding
import com.example.llegadasegura.principal.PrincipalActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class grupos_join : AppCompatActivity() {
    private lateinit var binding: ActivityGruposJoinBinding
    private lateinit var rol: String
    private lateinit var codigo: String
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGruposJoinBinding.inflate(layoutInflater)
        val bundle= intent.extras
        val correo = bundle?.getString("correo")
        setContentView(binding.root)
        binding.BotonContinuar.setOnClickListener{
            rol = binding.editTextRol.text.toString()
            codigo = binding.editTextCodigo.text.toString()
            buscarGrupo(codigo, rol, correo.toString())
            cambiarPrincipalActivity(correo.toString())
        }
    }

    private fun buscarGrupo(identificador:String, rol:String, correo:String ){
        Log.d("IngresaS", "Ingresa a los datos " + identificador +" "+ rol+ " "+ correo)
        val grupoDatos =db.collection("grupos").document(identificador).get()
        grupoDatos.addOnSuccessListener { documento ->
            val nombre2 = documento.data?.get("Nombre").toString()
            val tipo2 = documento.data?.get("Tipo").toString()

            db.collection("grupos").document(identificador)
                .collection("Miembros").document().set(hashMapOf(
                    "Rol" to rol,
                    "Correo" to correo,
                ))

        db.collection("users").document(correo).collection("Grupos")
            .document(identificador).set(
                hashMapOf("Rol" to rol,
                "Nombre" to nombre2,
                "Tipo" to tipo2,
            ))
        }
        grupoDatos.addOnFailureListener{
            Toast.makeText(this, "No se encontro el reg", Toast.LENGTH_LONG).show()
        }
    }

    private fun cambiarPrincipalActivity(correo:String) {
        val intent = Intent(this, PrincipalActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("correo", correo)
        startActivity(intent)
    }


}