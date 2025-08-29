package com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.detalleReceta

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.tabs.IngredientesFragment
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.ui.tabs.InstruccionesFragment

class DetallePagerAdapter(
    fragment: Fragment,
    private val ingredientes: ArrayList<String>,
    private val pasos: ArrayList<String>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            IngredientesFragment.Companion.newInstance(ingredientes)
        else
            InstruccionesFragment.Companion.newInstance(pasos)
    }
}