package com.example.llegadasegura.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.llegadasegura.databinding.ActivityLogin2Binding
import com.example.llegadasegura.principal.PrincipalActivity
import com.example.llegadasegura.registro.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityLogin2Binding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        val bundle= intent.extras
        val correo = bundle?.getString("correo")
        val contrasenia = binding.editTextTextPassword
        auth = Firebase.auth
        setContentView(binding.root)

        binding.btnContinuar.setOnClickListener{
            validarUsuario(correo.toString(),
                contrasenia.text.toString())
        }
    }
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser == null){
            Toast.makeText(this, "El usuario es nulo", Toast.LENGTH_LONG).show()
        }
    }
    private fun validarUsuario(email:String, password:String){
        Log.d("usuario", email +  password)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "Logeado Correctamente")
                    val user = auth.currentUser
                    updateUI(user)
                    Log.d("Usuario", user.toString())

                } else {
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Fallo el ingreso",
                        Toast.LENGTH_SHORT).show()

                    updateUI(null)
                }
            }
    }
    private fun updateUI(account: FirebaseUser?) {
        if (account != null) {
            Toast.makeText(this, "¡Log in exitoso!", Toast.LENGTH_LONG).show()
            cambiarActividadPrincipal(account.email.toString())
        } else {
            Toast.makeText(this, "¡Log in fallido!", Toast.LENGTH_LONG).show()
        }
    }
    private fun cambiarActividadPrincipal(correo:String) {
        val prefs = getSharedPreferences("loginData", Context.MODE_PRIVATE).edit()
        prefs.putString("email", correo)
        prefs.apply()
        val intent = Intent(this, PrincipalActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("correo", correo)
        startActivity(intent)
    }





}