package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.ListaModoAhorro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemModoAhorroBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel

class ListaModoAhorroAdapter(private var listModoAhorro: List<ComidasSeccionMenuModel> = emptyList()) :
    RecyclerView.Adapter<ListaModoAhorroViewHolder>() {

        fun updateListModoAhorro(list: List<ComidasSeccionMenuModel>){
            listModoAhorro = list
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaModoAhorroViewHolder {
        val binding = ItemModoAhorroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListaModoAhorroViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ListaModoAhorroViewHolder,
        position: Int
    ) {
        holder.render(listModoAhorro[position])
    }

    override fun getItemCount() = listModoAhorro.size
}