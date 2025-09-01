package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoMenu.listaClasicosArgentinos

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemClasicoArgentinoBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.FavoritosRequestModel
import com.squareup.picasso.Picasso
import io.github.jan.supabase.auth.Auth

class ListaClasicoArgentinoViewHolder(
    private val binding: ItemClasicoArgentinoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun render(
        comidasModel: ComidasSeccionMenuModel,
        onItemSelectedFav: (FavoritosRequestModel) -> Unit,
        auth: Auth
    ) {
        binding.tvComidaClasicoArgentino.text = comidasModel.titulo

        Picasso.get().load(comidasModel.imagen).into(binding.imgComidaClasicoArgentino)

        binding.imgAddClasicoArgentino.setOnClickListener {
            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener
            val favorito = FavoritosRequestModel(
                recetaId = comidasModel.id,
                usuarioId = userId
            )
            onItemSelectedFav(favorito)
        }
    }
}

