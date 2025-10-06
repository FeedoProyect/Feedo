package com.benjamin.proyectofeedo.PantallasPrincipales.data

import com.benjamin.proyectofeedo.PantallasPrincipales.data.repositoriosImpl.AddRecetasRepositoryImpl
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.AddRecetaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAddRecetaRepository(
        impl: AddRecetasRepositoryImpl
    ): AddRecetaRepository
}