package com.example.llegadasegura.principal.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.llegadasegura.R
import com.example.llegadasegura.databinding.FragmentConfigurationBinding
import com.example.llegadasegura.principal.PrincipalActivity
import com.example.llegadasegura.registro.MainActivity
import com.google.firebase.auth.FirebaseAuth


class ConfigurationFragment : Fragment() {

    private lateinit var binding: FragmentConfigurationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConfigurationBinding.inflate(layoutInflater, container, false)
        binding.btnCerrar.setOnClickListener{
            showAlert()
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


}