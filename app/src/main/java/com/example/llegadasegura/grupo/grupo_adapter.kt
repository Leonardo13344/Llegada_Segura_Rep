package com.example.llegadasegura.grupo

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.llegadasegura.Clases.Grupo
import com.example.llegadasegura.R
import com.example.llegadasegura.databinding.ItemGrupoBinding

class grupo_adapter(
    val grupo: List<Grupo>
) :
    RecyclerView.Adapter<grupo_adapter.GrupoHolder>() {



    inner class GrupoHolder(item: View) : RecyclerView.ViewHolder(item) {
        var binding: ItemGrupoBinding = ItemGrupoBinding.bind(item)
        fun render(grupo: Grupo) {
            binding.nombreGrupo.text = grupo.nombre
            binding.descripcionGrupo.text = grupo.tipo
            binding.rolGrupo.text = grupo.rol
            if (grupo.tipo == "Familia") {
                binding.imagenGrupo.setImageResource(R.drawable.ic_familia)
            } else if (grupo.tipo == "Amigos") {
                binding.imagenGrupo.setImageResource(R.drawable.ic_amigos)
            } else if (grupo.tipo == "Trabajo") {
                binding.imagenGrupo.setImageResource(R.drawable.ic_trabajo)
            }
            binding.btnTest.setOnClickListener {
                Log.d("GruposIds", "${grupo.id}")
                //fragment.idGroup = grupo.id.toString()
                var ctx: Context = this.itemView.context
                var editor: SharedPreferences.Editor = ctx.getSharedPreferences("GrupoIds", Context.MODE_PRIVATE).edit()
                editor.putString("idGrupo",grupo.id.toString())
                editor.apply()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrupoHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GrupoHolder(inflater.inflate(R.layout.item_grupo, parent, false))
    }

    override fun onBindViewHolder(holder: GrupoHolder, position: Int) {
        holder.render(grupo[position])
    }

    override fun getItemCount(): Int {
        return grupo.size
    }
}