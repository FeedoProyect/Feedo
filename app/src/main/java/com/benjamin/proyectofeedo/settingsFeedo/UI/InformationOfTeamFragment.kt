package com.benjamin.proyectofeedo.settingsFeedo.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.benjamin.proyectofeedo.databinding.FragmentInformationOfTeamBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InformationOfTeamFragment : Fragment() {

    private var _binding: FragmentInformationOfTeamBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvPronto.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInformationOfTeamBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}