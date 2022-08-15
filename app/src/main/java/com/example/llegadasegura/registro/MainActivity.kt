package com.example.llegadasegura.registro
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.llegadasegura.databinding.ActivityMainBinding
import android.content.Intent
import android.util.Log
import com.example.llegadasegura.login.Login2Activity
import com.example.llegadasegura.login.LoginActivity
import com.example.llegadasegura.principal.PrincipalActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener{
            cambiarPantallaRegistro2()
        }

        binding.btnLogin.setOnClickListener{
            nextScreen()
        }
        session()
    }

    private fun cambiarPantallaRegistro2(){
        var intent = Intent(this, Registro2::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent)
    }

    private fun nextScreen(){
        var intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent)
    }

    private fun autoLogin(correo:String){
        val intent = Intent(this, PrincipalActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("correo", correo)
        startActivity(intent)
    }

    private fun session(){
        val prefs = getSharedPreferences("loginData", Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        if(email != null){
            Log.d("Test", email.toString())
            autoLogin(email)
        }
    }
}