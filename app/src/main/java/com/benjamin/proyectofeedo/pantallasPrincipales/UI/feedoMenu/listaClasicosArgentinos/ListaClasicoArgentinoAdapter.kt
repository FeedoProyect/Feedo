package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.listaClasicosArgentinos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemClasicoArgentinoBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel

class ListaClasicoArgentinoAdapter(private var listClasicoArgentino: List<ComidasSeccionMenuModel> = emptyList()) :
    RecyclerView.Adapter<ListaClasicoArgentinoViewHolder>() {

    fun updateListClasicoArgentino(list: List<ComidasSeccionMenuModel>){
        listClasicoArgentino = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaClasicoArgentinoViewHolder {
        val binding = ItemClasicoArgentinoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListaClasicoArgentinoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ListaClasicoArgentinoViewHolder,
        position: Int
    ) {
        holder.render(listClasicoArgentino[position])
    }

    override fun getItemCount() = listClasicoArgentino.size

}