package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FavoritosFragment.listaFavoritosAdapter

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemFavoritosPerfilBinding
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.model.ComidasModel
import com.squareup.picasso.Picasso

class ListaFavoritosViewHolder(private val binding: ItemFavoritosPerfilBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(comidasModel: ComidasModel){
        binding.tvComidaFavoritos.text = comidasModel.titulo

        Picasso.get().load(comidasModel.imagen).into(binding.imgComidaFavoritos)
    }
}