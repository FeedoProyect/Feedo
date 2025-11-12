package com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.detalleReceta

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.tabs.TiempoFragment
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.tabs.PasosFragment
import com.benjamin.proyectofeedo.R

class InfoPagerAdapter(
    fragment: Fragment,
    private var tiempoPreparacion: String = "",
    private var totalPasos: Int = 0
) : FragmentStateAdapter(fragment) {

    private var imgIconoSecundario: ImageView? = null

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            TiempoFragment.newInstance(tiempoPreparacion)
        } else {
            PasosFragment.newInstance(totalPasos)
        }
    }

    fun updateData(tiempoPreparacion: String, totalPasos: Int) {
        this.tiempoPreparacion = tiempoPreparacion
        this.totalPasos = totalPasos
        notifyDataSetChanged()
    }

    fun attachImageView(imgIcono: ImageView, viewPager: ViewPager2) {
        this.imgIconoSecundario = imgIcono

        // Establecer imagen inicial
        updateIcono(0)

        // Escuchar cambios de página del ViewPager principal
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateIcono(position)
            }
        })
    }

    private fun updateIcono(position: Int) {
        imgIconoSecundario?.setImageResource(
            when(position) {
                0 -> R.drawable.ic_reloj
                else -> R.drawable.img_pasos_receta
            }
        )
    }
}