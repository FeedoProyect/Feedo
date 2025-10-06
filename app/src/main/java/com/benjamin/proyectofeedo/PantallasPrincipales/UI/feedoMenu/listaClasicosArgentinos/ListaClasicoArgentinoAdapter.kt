package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaClasicosArgentinos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.PantallaClasicoArgentino.listaDeComidasClasicoArgentino.ListaClasicoArgentinoViewHolder
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.databinding.ItemSeccionesMenuBinding
import io.github.jan.supabase.auth.Auth

class ListaClasicoArgentinoAdapter(
    private val auth: Auth,
    private var listClasicoArgentino: List<ComidasSeccionMenuModel> = emptyList(),
    private val onItemClick: (ComidasSeccionMenuModel) -> Unit,
    private val onItemSelectedFavs: (FavoritosRequestModel) -> Unit
) : RecyclerView.Adapter<ListaClasicoArgentinoViewHolder>() {

    fun updateListClasicoArgentino(list: List<ComidasSeccionMenuModel>) {
        listClasicoArgentino = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaClasicoArgentinoViewHolder {
        val binding = ItemSeccionesMenuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListaClasicoArgentinoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListaClasicoArgentinoViewHolder, position: Int) {
        val item = listClasicoArgentino[position]
        holder.render(item, auth, onItemSelectedFavs)

        // Click general del ítem (por ejemplo, para abrir detalle)
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }


    override fun getItemCount() = listClasicoArgentino.size
}
