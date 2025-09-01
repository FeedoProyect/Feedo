package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoContenidoCatalogos.listaComidaDestacadaCatalogoAdapter

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemComidaDestacadaCatalogoBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidaDestacadaCatalogoModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.FavoritosRequestModel
import com.squareup.picasso.Picasso
import io.github.jan.supabase.auth.Auth

class ComidaDestacadaCatalogoViewHolder(private val binding: ItemComidaDestacadaCatalogoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(
        comidaDestacadaCatalogoInfo: ComidaDestacadaCatalogoModel,
        onItemSelectedFav: (FavoritosRequestModel) -> Unit,
        auth: Auth
    ) {
        binding.tvTituloComidaDestacadaCatalogo.text = comidaDestacadaCatalogoInfo.titulo
        binding.tvTiempoComidaDestacadaCatalogo.text = comidaDestacadaCatalogoInfo.tiempo
        Picasso.get().load(comidaDestacadaCatalogoInfo.imagen)
            .into(binding.imgComidaDestacadaCatalogo)

        binding.imgAddComidaDestacadaCatalogo.setOnClickListener {
            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener
            val favorito = FavoritosRequestModel(
                recetaId = comidaDestacadaCatalogoInfo.id,
                usuarioId = userId
            )
            onItemSelectedFav(favorito)
        }
    }
}