package com.example.llegadasegura.grupo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.llegadasegura.Clases.Grupo
import com.example.llegadasegura.databinding.ActivityGruposCreateBinding
import com.example.llegadasegura.principal.PrincipalActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class grupos_create : AppCompatActivity() {
    private lateinit var binding: ActivityGruposCreateBinding
    private lateinit var tipo: String
    private lateinit var nombre:String
    private lateinit var rol:String
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGruposCreateBinding.inflate(layoutInflater)
        val bundle= intent.extras
        val correo = bundle?.getString("correo")
        var num = 1
        val datosGrupos = db.collection("users").document(correo.toString()).collection("Grupos")
        datosGrupos.get().addOnSuccessListener { documents ->
            for (document in documents) {
                num++
            }
        }

        binding.btnContinuar.setOnClickListener{
            Log.d("Boton" ,"Toco el boton")
            if(binding.radioBtnAmigos.isChecked){
                tipo="Amigos"
            }else if (binding.radioBtnFamilia.isChecked){
                tipo="Familia"
            }else if(binding.radioBtnTrabajo.isChecked){
                tipo="Trabajo"
            }else{
                Toast.makeText(this,"Error Tipo", Toast.LENGTH_LONG).show()
            }
            rol = binding.editTextRol.text.toString()
            nombre = binding.editTextNombreGrupo.text.toString()
            Log.d("Datos Ingresados" , "$rol $nombre $tipo")
            val aux = Grupo(num.toString(), rol,nombre,tipo)
            registrarDatosD(correo.toString(), aux, num.toString())
            cambiarPrincipalActivity(correo.toString())
        }
        setContentView(binding.root)
    }
    private fun cambiarPrincipalActivity(correo:String) {
        val intent = Intent(this, PrincipalActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("correo", correo)
        startActivity(intent)
    }
    private fun registrarDatosD(correo: String, grupo: Grupo, id:String) {
        Log.d("Entra","Entra a registrar")
        //db.collection("users").document().id
        db.collection("users").document(correo)
            .collection("Grupos").document(id).set(
                hashMapOf(
                    "Nombre" to grupo.nombre,
                    "Rol" to grupo.rol,
                    "Tipo" to grupo.tipo,
                )
            )
        db.collection("grupos").document(id).set(
            hashMapOf("Nombre" to grupo.nombre,
            "IdGrupo" to id,
            "CorreoCreador" to correo,
            )
        )
        //Toast.makeText(this,"IngresaAlMetodoGuardar",Toast.LENGTH_LONG).show()
    }
}