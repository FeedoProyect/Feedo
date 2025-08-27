package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.listaComidaExpres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemExpresBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel

class ListaExpresAdapter(private var listaExpres: List<ComidasSeccionMenuModel> = emptyList()):
    RecyclerView.Adapter<ListaExpresViewHolder>() {

    fun updateListExpres(list: List<ComidasSeccionMenuModel>){
        listaExpres = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaExpresViewHolder {
        val binding = ItemExpresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListaExpresViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ListaExpresViewHolder,
        position: Int
    ) {
        holder.render(listaExpres[position])
    }

    override fun getItemCount() = listaExpres.size
}