package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoContenidoCatalogos.listaDeComidasCatalogosAdapter

import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.databinding.ItemComidasCatalogosBinding
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.R
import com.squareup.picasso.Picasso
import io.github.jan.supabase.auth.Auth

class CatalogosListComidasViewHolder(private val binding: ItemComidasCatalogosBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun render(
        comidasModel: ComidasModel,
        onItemSelectedFav: (FavoritosRequestModel) -> Unit,
        auth: Auth
    ) {
        binding.tvComidaName.text = comidasModel.titulo
        Picasso.get().load(comidasModel.imagen).error(R.drawable.img_error).into(binding.imgComidaCatalogo)

        binding.imgAgregarFavComidaCatalogo.setOnClickListener {
            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener
            val favorito = FavoritosRequestModel(
                recetaId = comidasModel.id,
                usuarioId = userId
            )
            onItemSelectedFav(favorito)
        }
    }
}


//proyecto a largo plazo lo de abajo

// Cambiar ícono dependiendo si es favorito o no

//        val iconRes = if (comidasModel.isFavorite) {
//            R.drawable.ic_fav_filled
//        } else {
//            R.drawable.ic_fav_outline
//        }
//        binding.imgFav.setImageResource(iconRes)