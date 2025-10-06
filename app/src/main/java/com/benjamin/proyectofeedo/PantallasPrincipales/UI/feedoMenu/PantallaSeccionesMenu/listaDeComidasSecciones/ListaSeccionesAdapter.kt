package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.PantallaSeccionesMenu.listaDeComidasSecciones

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.databinding.ItemComidaSeccionesBinding
import com.benjamin.proyectofeedo.databinding.ItemSeccionesMenuBinding
import io.github.jan.supabase.auth.Auth

class ListaSeccionesAdapter(
    private val auth: Auth,
    private val onItemClick: (ComidasSeccionMenuModel) -> Unit,
    private val onItemSelectedFav: (FavoritosRequestModel) -> Unit
) : RecyclerView.Adapter<ListaSeccionesViewHolder>() {

    private var comidaSeccionesList: List<ComidasSeccionMenuModel> = emptyList()

    fun updateList(list: List<ComidasSeccionMenuModel>){
        comidaSeccionesList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaSeccionesViewHolder {
        val binding = ItemSeccionesMenuBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
    )
        return ListaSeccionesViewHolder(binding)

    }


    override fun onBindViewHolder(
        holder: ListaSeccionesViewHolder,
        position: Int
    ) {
        val item = comidaSeccionesList[position]

        holder.render(item, auth, onItemSelectedFav)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount() = comidaSeccionesList.size
}