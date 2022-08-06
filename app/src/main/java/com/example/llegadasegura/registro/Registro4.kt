package com.example.llegadasegura.registro

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.llegadasegura.databinding.ActivityRegistro4Binding

class Registro4: AppCompatActivity() {
    private lateinit var binding: ActivityRegistro4Binding
    private lateinit var correo: EditText
    private lateinit var contrasenia: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistro4Binding.inflate(layoutInflater)
        correo = binding.editTextCorreo
        contrasenia = binding.editTextContrasenia
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val bundle= intent.extras
        val numero = bundle?.getString("numero")
        val nombre = bundle?.getString("nombre")
        val apellido = bundle?.getString("apellido")
        binding.btnContinuar.setOnClickListener{
            if (validarContrasenia(contrasenia.text.toString())){
                cambiarPantallaRegistro4(numero.toString(),nombre.toString(),apellido.toString(),correo.text.toString().trim(), contrasenia.text.toString().trim())
            }else{
                Toast.makeText(this,"La contraseña tiene que ser +8 Caracteres", Toast.LENGTH_LONG).show()
            }


        }
    }
    private fun cambiarPantallaRegistro4(numero:String, nombre:String, apellido:String, correo:String, contrasenia:String){
        val intent = Intent(this, Registro5::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("numero", numero)
        intent.putExtra("nombre", nombre)
        intent.putExtra("apellido", apellido)
        intent.putExtra("correo", correo)
        intent.putExtra("contrasenia", contrasenia)
        startActivity(intent)
    }
    private fun validarContrasenia(contrasenia:String):Boolean {
        return (contrasenia.length>8&&!contrasenia.contains(" "))
    }
}