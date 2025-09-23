package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FavoritosFragment.listaFavoritosAdapter

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemFavoritosPerfilBinding
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosReceta
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.squareup.picasso.Picasso
import io.github.jan.supabase.auth.Auth

class ListaFavoritosViewHolder(private val binding: ItemFavoritosPerfilBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(
        comidasModel: FavoritosReceta,
        onDeleteClick: (FavoritosRequestModel) -> Unit,
        auth: Auth
    ){
        binding.tvComidaFavoritos.text = comidasModel.titulo

        Picasso.get().load(comidasModel.imagen).into(binding.imgComidaFavoritos)

        binding.imgDeleteFavComida.setOnClickListener {

            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener
            val deleteFavorito = FavoritosRequestModel(
                recetaId = comidasModel.id,
                usuarioId = userId
            )
            onDeleteClick(deleteFavorito)
        }
    }
}