package com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddIngredienteFragment.AddIngredienteFragment
import com.benjamin.proyectofeedo.pantallasPrincipales.UI.feedoAddRecetas.subFragmentTabLayout.AddInstruccionesFragment.AddInstruccionesFragment

class FragmentPageAddRecetaAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            AddIngredienteFragment()
        } else {
            AddInstruccionesFragment()
        }
    }

    override fun getItemCount() = 2

}