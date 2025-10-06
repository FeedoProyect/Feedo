package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoPerfil.subFragmentTabLayout.FavoritosFragment.listaFavoritosAdapter

import android.animation.ValueAnimator
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
        auth: Auth,
        onItemSelectedFav: (FavoritosRequestModel, Boolean) -> Unit
    ) {
        binding.tvComidaFavoritos.text = receta.titulo

        Picasso.get()
            .load(receta.imagen)
            .error(R.drawable.img_error)
            .into(binding.imgComidaFavoritos)

        // en favoritos siempre empieza marcado
        isFav = true
        updateHeartState(true)

        binding.btnFav.setOnClickListener {
            isFav = !isFav

            // animación del botón
            val scaleAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
                duration = 200
                addUpdateListener { anim ->
                    val scale = anim.animatedValue as Float
                    binding.btnFav.scaleX = scale
                    binding.btnFav.scaleY = scale
                }
            }
            scaleAnimator.start()

            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener
            val favRequest = FavoritosRequestModel(recetaId = receta.id, usuarioId = userId)

            onItemSelectedFav(favRequest, isFav)
            updateHeartState(isFav)
        }
    }

    private fun updateHeartState(isFav: Boolean) {
        val icon = if (isFav) R.drawable.ic_heart_full else R.drawable.ic_fav_border
        binding.btnFav.setImageResource(icon)
    }
}



