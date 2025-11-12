package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.holders

import android.graphics.drawable.Animatable
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.R

import com.benjamin.proyectofeedo.databinding.ItemSeccionesMenuBinding
import com.squareup.picasso.Picasso
import io.github.jan.supabase.auth.Auth

class CatalogosListComidasViewHolder(
    private val binding: ItemSeccionesMenuBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun render(
        comidasModel: ComidasModel,
        auth: Auth,
        onItemSelected: (ComidasModel) -> Unit,
        onItemSelectedFav: (FavoritosRequestModel, Boolean) -> Unit
    ) {

        binding.tvComidaSeccionesMenu.text = comidasModel.titulo
        Picasso.get()
            .load(comidasModel.imagen)
            .error(R.drawable.img_error)
            .into(binding.imgComidaSeccionesMenu)


        setFavoriteIcon(comidasModel.esFavorito)


        binding.root.setOnClickListener {
            onItemSelected(comidasModel)
        }


        binding.cardFavBackground.setOnClickListener {
            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener

            val nuevoEstado = !comidasModel.esFavorito
            comidasModel.esFavorito = nuevoEstado

            setFavoriteIcon(nuevoEstado)
            animateHeartSmooth(nuevoEstado)

            val favorito = FavoritosRequestModel(
                recetaId = comidasModel.id,
                usuarioId = userId
            )
            onItemSelectedFav(favorito, nuevoEstado)
        }
    }


    private fun setFavoriteIcon(fav: Boolean) {
        val drawableRes = if (fav) R.drawable.ic_heart_full else R.drawable.ic_heart_empty
        binding.iconFav.setImageResource(drawableRes)
    }


    private fun animateHeartSmooth(fav: Boolean) {
        binding.iconFav.animate()
            .scaleX(0.8f)
            .scaleY(0.8f)
            .alpha(0.8f)
            .setDuration(100)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                val drawableRes = if (fav) R.drawable.avd_heart_fill else R.drawable.avd_heart_unfill
                binding.iconFav.setImageResource(drawableRes)
                (binding.iconFav.drawable as? Animatable)?.start()

                binding.iconFav.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .alpha(1f)
                    .setDuration(250)
                    .setInterpolator(OvershootInterpolator())
                    .start()
            }
            .start()
    }
}



