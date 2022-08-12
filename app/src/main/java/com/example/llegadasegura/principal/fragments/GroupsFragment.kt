package com.example.llegadasegura.principal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.llegadasegura.Clases.Grupo
import com.example.llegadasegura.R
import com.example.llegadasegura.databinding.FragmentGroupsBinding
import com.example.llegadasegura.grupo.grupo_adapter


class GroupsFragment : Fragment() {

    private lateinit var binding: FragmentGroupsBinding

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<grupo_adapter.GrupoHolder>? = null

    private var grupos : List<Grupo> = listOf(
        Grupo("01","Padre","Familia"),
        Grupo("02","Madre","Familia"),
        Grupo("03","Hermano","Familia"),
        Grupo("04","Amigo","Amigos"),

        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupsBinding.inflate(layoutInflater, container,false)
        // Inflate the layout for this fragment
        initRecycler(binding.root)
        return binding.root
    }

    private fun initRecycler(itemView: View){
        val recyclerView = itemView.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = grupo_adapter(grupos)
        recyclerView.adapter = adapter
    }


    }




