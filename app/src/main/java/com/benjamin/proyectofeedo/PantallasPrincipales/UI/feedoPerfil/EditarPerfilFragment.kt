package com.benjamin.proyectofeedo.PantallasPrincipales.UI.feedoPerfil

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.benjamin.proyectofeedo.R
import com.benjamin.proyectofeedo.databinding.FragmentEditarPerfilBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class EditarPerfilFragment : Fragment() {

    private var _binding: FragmentEditarPerfilBinding? = null
    private val binding get() = _binding!!

    private val perfilViewModel: PerfilViewModel by activityViewModels()

    private var selectedImageUri: Uri? = null
    private var capturedImageBytes: ByteArray? = null

    // Permiso galería
    private val requestGalleryPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) openGallery()
            else Toast.makeText(requireContext(), "Permiso denegado", Toast.LENGTH_SHORT).show()
        }


    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) takePhotoByIntent()
            else Toast.makeText(requireContext(), "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }


    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == android.app.Activity.RESULT_OK) {
                selectedImageUri = result.data?.data
                selectedImageUri?.let {
                    capturedImageBytes = null

                    binding.imgProfile.setImageURI(it)
                }
            }
        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == android.app.Activity.RESULT_OK) {
                val photo = result.data?.extras?.get("data") as? Bitmap
                photo?.let {
                    val out = ByteArrayOutputStream()
                    it.compress(Bitmap.CompressFormat.JPEG, 85, out)
                    capturedImageBytes = out.toByteArray()
                    selectedImageUri = null

                    binding.imgProfile.setImageBitmap(it)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditarPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        perfilViewModel.userInfo.value?.let { user ->
            binding.edtName.setText(user.username)
            binding.edtBio.setText(user.biografia ?: "")
            if (!user.imagen_perfil.isNullOrBlank()) {
                val fotoUrl = "${user.imagen_perfil}?t=${System.currentTimeMillis()}"


                binding.progressBarFoto.visibility = View.VISIBLE
                binding.imgProfile.visibility = View.INVISIBLE

                Picasso.get()
                    .load(fotoUrl)
                    .placeholder(R.drawable.agregarperfil)
                    .error(R.drawable.imagen_sin_perfil)
                    .into(binding.imgProfile, object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            binding.progressBarFoto.visibility = View.GONE
                            binding.imgProfile.visibility = View.VISIBLE
                        }

                        override fun onError(e: Exception?) {
                            binding.progressBarFoto.visibility = View.GONE
                            binding.imgProfile.visibility = View.VISIBLE
                            binding.imgProfile.setImageResource(R.drawable.imagen_sin_perfil)
                        }
                    })
            } else {

                binding.progressBarFoto.visibility = View.GONE
                binding.imgProfile.visibility = View.VISIBLE
                binding.imgProfile.setImageResource(R.drawable.agregarperfil)
            }
        }

        initListeners()
    }

    private fun initListeners() {
        binding.imgProfile.setOnClickListener {
            val options = arrayOf("Elegir de galería", "Tomar foto")
            AlertDialog.Builder(requireContext())
                .setTitle("Seleccionar imagen")
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> checkGalleryPermission()
                        1 -> checkCameraPermission()
                    }
                }.show()
        }

        binding.btnSave.setOnClickListener {
            val nuevoNombre = binding.edtName.text.toString().trim()
            val nuevaBio = binding.edtBio.text.toString().trim()

            perfilViewModel.uploadImageAndUpdateUser(
                context = requireContext(),
                imageUri = selectedImageUri,
                imageBytes = capturedImageBytes,
                username = nuevoNombre,
                biografia = nuevaBio
            )


            findNavController().popBackStack()
        }
    }

    private fun checkGalleryPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openGallery()
            } else {
                requestGalleryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openGallery()
            } else {
                requestGalleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            takePhotoByIntent()
        } else {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    private fun takePhotoByIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





