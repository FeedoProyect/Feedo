package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoBuscador.BuscadorPrincipalAdapter

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemComidaBuscadorBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.FavoritosRequestModel
import com.squareup.picasso.Picasso
import io.github.jan.supabase.auth.Auth

class BuscadorPrincipalViewHolder(private val binding: ItemComidaBuscadorBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(
        comidaBuscadorInfo: ComidasModel,
        onItemSelectedFav: (FavoritosRequestModel) -> Unit,
        auth: Auth
    ) {
        binding.tvComidaBuscador.text = comidaBuscadorInfo.titulo
        Picasso.get().load(comidaBuscadorInfo.imagen).into(binding.imgComidaBuscador)

        binding.imgAddFavsBuscador.setOnClickListener {
            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener
            val favorito = FavoritosRequestModel(
                recetaId = comidaBuscadorInfo.id,
                usuarioId = userId
            )
            onItemSelectedFav(favorito)
        }
    }
}