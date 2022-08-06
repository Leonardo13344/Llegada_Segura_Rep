package com.example.llegadasegura.grupo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.llegadasegura.Clases.Grupo
import com.example.llegadasegura.databinding.ActivityGruposBinding

class Grupos : AppCompatActivity() {

    private var grupos : List<Grupo> = listOf(
        Grupo("01","Padre","Familia"),
        Grupo("02","Madre","Familia"),
        Grupo("03","Hermano","Familia"),
        Grupo("04","Amigo","Amigos"),

    )
    private lateinit var binding: ActivityGruposBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGruposBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecicler()
    }

    fun initRecicler(){

        binding.rvGrupos.layoutManager = LinearLayoutManager(this)
        val adapter = grupo_adapter(grupos)
        binding.rvGrupos.adapter = adapter
    }
}