package com.example.llegadasegura.principal.fragments


import android.content.Intent
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
import com.example.llegadasegura.grupo.grupos_create
import com.example.llegadasegura.grupo.grupos_join



class GroupsFragment : Fragment(){

    private lateinit var binding: FragmentGroupsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGroupsBinding.inflate(layoutInflater, container,false)
        val args = arguments
        val myList :ArrayList<Grupo> = args?.getParcelableArrayList<Grupo>("list") as ArrayList<Grupo>



        var correo: String? = args.getString("correo")
        // Inflate the layout for this fragment


        initRecycler(binding.root, myList)
        binding.nuevoGrupoBoton.setOnClickListener{
            nextScreen(correo.toString())
        }
        binding.BtnUnirseGrupo.setOnClickListener {
            nextScreenUnirse(correo.toString())
        }
        return binding.root
    }

    private fun initRecycler(itemView: View, myList: ArrayList<Grupo>){
        val recyclerView = itemView.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = grupo_adapter(myList)
        recyclerView.adapter = adapter
    }
    private fun nextScreen(correo:String){
        activity?.let{
            val intent = Intent (it, grupos_create::class.java)
            intent.putExtra("correo",correo)
            it.startActivity(intent)
        }
    }
    private fun nextScreenUnirse(correo:String){
        activity?.let{
            val intent = Intent (it, grupos_join::class.java)
            intent.putExtra("correo",correo)
            it.startActivity(intent)
        }
    }




}




