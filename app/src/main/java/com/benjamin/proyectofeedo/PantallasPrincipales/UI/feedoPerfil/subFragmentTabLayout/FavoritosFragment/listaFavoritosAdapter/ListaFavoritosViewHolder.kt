package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FavoritosFragment.listaFavoritosAdapter

import android.animation.ValueAnimator
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.ItemFavoritosPerfilBinding
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosReceta
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.squareup.picasso.Picasso
import io.github.jan.supabase.auth.Auth

class ListaFavoritosViewHolder(private val binding: ItemFavoritosPerfilBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private var isFav = true

    fun render(
        receta: FavoritosReceta,
        onDeleteClick: (FavoritosRequestModel) -> Unit,
        onItemClick: (FavoritosReceta) -> Unit,
        auth: Auth
    ) {
        binding.tvComidaFavoritos.text = receta.titulo

        Picasso.get()
            .load(receta.imagen)
            .error(R.drawable.img_error)
            .into(binding.imgComidaFavoritos)

        updateHeartState(isFav)

        binding.btnFav.setOnClickListener {
            isFav = !isFav

            val scaleAnimator = ValueAnimator.ofFloat(
                if (isFav) 0f else 1f,
                if (isFav) 1f else 0f
            )
            scaleAnimator.duration = 250
            scaleAnimator.addUpdateListener { anim ->
                val scale = anim.animatedValue as Float
                binding.btnFav.scaleX = scale
                binding.btnFav.scaleY = scale
            }
            scaleAnimator.start()

            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener

            if (isFav) {
                updateHeartState(true)
            } else {
                val deleteFavorito = FavoritosRequestModel(
                    recetaId = receta.id,
                    usuarioId = userId
                )
                onDeleteClick(deleteFavorito)
                updateHeartState(false)
            }
        }

        binding.root.setOnClickListener {
            onItemClick(receta)
        }
    }

    private fun updateHeartState(isFav: Boolean) {
        val icon = if (isFav) R.drawable.ic_favs_filled else R.drawable.ic_fav_border
        binding.btnFav.setImageResource(icon)
    }
}

