package com.example.llegadasegura.principal.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.llegadasegura.R
import com.example.llegadasegura.databinding.FragmentConfigurationBinding
import com.example.llegadasegura.principal.PrincipalActivity
import com.example.llegadasegura.registro.MainActivity
import com.example.llegadasegura.utils.AcercaDe
import com.example.llegadasegura.utils.Cuenta
import com.example.llegadasegura.utils.TerminosResultActivityInfo
import com.example.llegadasegura.utils.VersionPremium
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ConfigurationFragment : Fragment() {

    private lateinit var binding: FragmentConfigurationBinding
    private lateinit var nombre:String
    private lateinit var apellido:String
    private lateinit var telefono:String
    private lateinit var correo:String
    private val db = Firebase.firestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentConfigurationBinding.inflate(layoutInflater, container, false)
        //Se llenan los datos

        llenarDatos()
        binding.btnCerrar.setOnClickListener{
            showAlert()
        }
        binding.btnPoliticas.setOnClickListener{
            irTerminos()
        }
        binding.btnCuenta.setOnClickListener{
            irCuenta(nombre, apellido, telefono, correo)
        }
        binding.btnSoporte.setOnClickListener{
            irAAcercaDe()
        }
        binding.btnPremium.setOnClickListener{
            irVersionPremium()
        }
        return binding.root
    }

    private fun retorno(){
        val prefs = requireContext().getSharedPreferences("loginData", Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Alerta")
        builder.setMessage("¿Estas seguro de que quieres salir?")
        builder.setPositiveButton("Aceptar", {_,_ -> retorno()})
        builder.setNegativeButton("Cancelar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun irTerminos(){
        val intent = Intent(requireContext(), TerminosResultActivityInfo::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
    private fun irAAcercaDe(){
        val intent = Intent(requireContext(), AcercaDe::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun llenarDatos() {
        val usuario = FirebaseAuth.getInstance().currentUser?.email.toString()
        correo = usuario
        nombre = ""
        apellido =""
        telefono =""
        db.collection("users").document(usuario).get().addOnSuccessListener {
                document ->
            if (document != null) {
                nombre = document.data?.get("Nombre").toString()
                apellido = document.data?.get("apellido").toString()
                telefono = document.data?.get("telefono").toString()
                Log.d("documento", "DocumentSnapshot data: ${document.data}")
                Log.d("documentoD", "DocumentSnapshot data: ${nombre} ${apellido} ${telefono} ")
            } else {
                Log.d("documento", "No such document")
            }
        }
        Log.d("UsuarioCuenta", nombre)
    }
    private fun irCuenta(nombre:String, apellido:String, telefono:String, correo:String) {

        val intent = Intent(requireContext(), Cuenta::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("correo", correo)
        intent.putExtra("nombre", nombre)
        intent.putExtra("apellido", apellido)
        intent.putExtra("telefono", telefono)
        Log.d("DatosPreviosIntent", "DocumentSnapshot data: ${nombre} ${apellido} ${telefono} ")
        startActivity(intent)
    }
    private fun irVersionPremium(){
        val intent = Intent(requireContext(), VersionPremium::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}