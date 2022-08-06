package com.example.llegadasegura.registro

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.llegadasegura.Clases.Direccion
import com.example.llegadasegura.Clases.Grupo
import com.example.llegadasegura.Clases.usuario
import com.example.llegadasegura.databinding.ActivityRegistro5Binding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Registro5: AppCompatActivity() {
    private lateinit var binding: ActivityRegistro5Binding
    private lateinit var codAct: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser:FirebaseUser
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistro5Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth
        val bundle = intent.extras
        val numero = bundle?.getString("numero")
        val nombre = bundle?.getString("nombre")
        val apellido = bundle?.getString("apellido")
        val correo = bundle?.getString("correo")
        val contrasenia = bundle?.getString("contrasenia")

        binding.btnContinuar.setOnClickListener {
            cambiarPantallaInicio()
            registrarUsuario(correo.toString(), contrasenia.toString())
            registrarDatosD(crearUsuario(correo.toString(),numero.toString(), nombre.toString(), apellido.toString()))
        }
    }

    private fun cambiarPantallaInicio() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun registrarDatosD(user:usuario){
        db.collection("users").document(user.correo).set(
            hashMapOf("Nombre" to user.nombre,
            "apellido" to user.apellido,
            "telefono" to user.numero,
            ))
        db.collection("users").document(user.correo).collection("Grupos")
            .document().set(Grupo())
        db.collection("users").document(user.correo).collection("Direcciones")
            .document().set(Direccion())

    }
    private fun crearUsuario(correo:String, numero:String, nombre:String, apellido:String):usuario{

       return usuario(correo, numero, nombre, apellido)
    }

    private fun registrarUsuario(correo:String, contrasenia:String){
        this.auth.createUserWithEmailAndPassword(correo, contrasenia).addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                Toast.makeText(this,"Se registro correctamente", Toast.LENGTH_LONG).show()
                this.firebaseUser = this.auth.currentUser!!
            } else {
                Toast.makeText(this,"No se pudo registrar USER ", Toast.LENGTH_LONG).show()
            }
        }
    }
}