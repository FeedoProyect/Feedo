package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoMenu.listaComidaExpres

import android.graphics.drawable.Animatable
import androidx.recyclerview.widget.RecyclerView
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.ComidasSeccionMenuModel
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.model.FavoritosRequestModel
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.ItemSeccionesMenuBinding
import com.squareup.picasso.Picasso
import io.github.jan.supabase.auth.Auth

class ListaExpresViewHolder(
    private val binding: ItemSeccionesMenuBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun render(
        comidasModel: ComidasSeccionMenuModel,
        onItemSelectedFav: (FavoritosRequestModel, Boolean) -> Unit, // ← pasamos si agrega o elimina
        auth: Auth
    ) {
        // Nombre e imagen
        binding.tvComidaSeccionesMenu.text = comidasModel.recetas.titulo
        Picasso.get()
            .load(comidasModel.recetas.imagen)
            .error(R.drawable.img_error)
            .into(binding.imgComidaSeccionesMenu)

        // Estado inicial
        setFavoriteIcon(comidasModel.recetas.esFavorito)

        // Click en el corazón
        binding.iconFav.setOnClickListener {
            val userId = auth.currentUserOrNull()?.id ?: return@setOnClickListener
            val favorito = FavoritosRequestModel(
                recetaId = comidasModel.recetas.id,
                usuarioId = userId
            )

            // ✅ Alternar estado local
            val nuevoEstado = !comidasModel.recetas.esFavorito
            comidasModel.recetas.esFavorito = nuevoEstado

            // ✅ Actualiza visualmente
            setFavoriteIcon(nuevoEstado)
            animateHeart(nuevoEstado)

            // ✅ Notifica si fue agregado (true) o eliminado (false)
            onItemSelectedFav(favorito, nuevoEstado)

            // ✅ Refresca el ítem del RecyclerView
            (itemView.parent as? RecyclerView)?.adapter?.notifyItemChanged(adapterPosition)

        }
    }

    /** Cambia el ícono del corazón según el estado actual */
    private fun setFavoriteIcon(fav: Boolean) {
        val drawableRes = if (fav) R.drawable.ic_heart_full else R.drawable.ic_heart_empty
        binding.iconFav.setImageResource(drawableRes)
    }

    /** Reproduce la animación del corazón */
    private fun animateHeart(fav: Boolean) {
        val drawableRes = if (fav) R.drawable.avd_heart_fill else R.drawable.avd_heart_unfill
        binding.iconFav.setImageResource(drawableRes)
        val drawable = binding.iconFav.drawable
        if (drawable is Animatable) drawable.start()
    }
}


