package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaEspecialMate

import android.graphics.drawable.Animatable
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.ItemSeccionesMenuBinding
import com.squareup.picasso.Picasso
import io.github.jan.supabase.auth.Auth

class ListaEspecialMateViewHolder(
    private val binding: ItemSeccionesMenuBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun render(
        comidasModel: ComidasSeccionMenuModel,
        auth: Auth,
        onItemSelectedFav: (FavoritosRequestModel, Boolean) -> Unit
    )
 {
        // 🧉 Cargar datos del ítem
        binding.tvComidaSeccionesMenu.text = comidasModel.recetas.titulo
        Picasso.get()
            .load(comidasModel.recetas.imagen)
            .error(R.drawable.img_error)
            .into(binding.imgComidaSeccionesMenu)

        // ❤️ Estado inicial
        setFavoriteIcon(comidasModel.recetas.esFavorito)

        // ❤️ Click del corazón
        binding.cardFavBackground.setOnClickListener {
            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener
            val nuevoEstado = !comidasModel.recetas.esFavorito
            comidasModel.recetas.esFavorito = nuevoEstado

            // Actualiza UI + animación
            setFavoriteIcon(nuevoEstado)
            animateHeartSmooth(nuevoEstado)

            // Crea modelo de favorito
            val favorito = FavoritosRequestModel(
                recetaId = comidasModel.recetas.id,
                usuarioId = userId
            )

            onItemSelectedFav(favorito, nuevoEstado)
        }
    }

    /** 🔹 Pinta el ícono según el estado */
    private fun setFavoriteIcon(fav: Boolean) {
        val drawableRes = if (fav) R.drawable.ic_heart_full else R.drawable.ic_heart_empty
        binding.iconFav.setImageResource(drawableRes)
    }

    /** 💖 Animación fluida */
    private fun animateHeartSmooth(fav: Boolean) {
        binding.iconFav.animate()
            .scaleX(0.8f)
            .scaleY(0.8f)
            .alpha(0.8f)
            .setDuration(100)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                val drawableRes =
                    if (fav) R.drawable.avd_heart_fill else R.drawable.avd_heart_unfill
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


