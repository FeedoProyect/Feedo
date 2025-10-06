package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaEspecialMate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.databinding.ItemSeccionesMenuBinding
import io.github.jan.supabase.auth.Auth

class ListaEspecialMateAdapter(
    private val auth: Auth,
    private var listaEspecialMate: List<ComidasSeccionMenuModel> = emptyList(),
    private val onItemClick: (ComidasSeccionMenuModel) -> Unit,
    private val onItemSelectedFavs: (FavoritosRequestModel, Boolean) -> Unit // ✅ correcto
) : RecyclerView.Adapter<ListaEspecialMateViewHolder>() {

    /** 🔁 Actualiza la lista */
    fun updateListEspecialMate(list: List<ComidasSeccionMenuModel>) {
        listaEspecialMate = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaEspecialMateViewHolder {
        val binding = ItemSeccionesMenuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListaEspecialMateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListaEspecialMateViewHolder, position: Int) {
        val item = listaEspecialMate[position]

        // ✅ Callback que devuelve el favorito y su estado (true/false)
        holder.render(item, auth) { favorito, isFav ->
            // Actualizamos la lista local con el nuevo estado
            listaEspecialMate[position].recetas.esFavorito = isFav
            notifyItemChanged(position)

            // Notificamos al fragment el cambio (para actualizar Supabase)
            onItemSelectedFavs(favorito, isFav)
        }

        // 👇 Click sobre el ítem completo
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount() = listaEspecialMate.size
}

