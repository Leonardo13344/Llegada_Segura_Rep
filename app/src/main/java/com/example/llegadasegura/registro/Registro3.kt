package com.example.llegadasegura.registro

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.llegadasegura.databinding.ActivityRegistro3Binding

class Registro3: AppCompatActivity() {
    private lateinit var binding: ActivityRegistro3Binding
    private lateinit var nombre: EditText
    private lateinit var apellido: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistro3Binding.inflate(layoutInflater)
        nombre = binding.editTextNombre
        apellido = binding.editTextApellido
        setContentView(binding.root)
        val bundle= intent.extras
        val numero = bundle?.getString("numero")

        binding.btnContinuar.setOnClickListener{
            if(validaNombreApellido()){
                cambiarPantallaRegistro4(numero.toString().trim(),nombre.text.toString().trim(),apellido.text.toString().trim())
            }else{
                Toast.makeText(this,"Ingrese su nombre y apellido",Toast.LENGTH_LONG).show()
            }

        }

    }


    private fun cambiarPantallaRegistro4(numero:String, nombre:String, apellido:String) {
        val intent = Intent(this, Registro4::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("numero", numero)
        intent.putExtra("nombre", nombre)
        intent.putExtra("apellido", apellido)
        startActivity(intent)
    }

    private fun validaNombreApellido():Boolean{
        return(binding.editTextNombre.text.toString().isNotEmpty() && binding.editTextApellido.text.toString().isNotEmpty())
    }
}