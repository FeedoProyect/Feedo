package com.benjamin.proyectofeedo.core.di

import com.benjamin.proyectofeedo.PantallaDetalleDeComida.data.repositoriosImpl.DetalleComidaRepositoryImpl
import com.benjamin.proyectofeedo.PantallaDetalleDeComida.domain.DetalleComidaRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDetalleComidaRepository(
        supabase: SupabaseClient
    ): DetalleComidaRepository {
        return DetalleComidaRepositoryImpl(supabase)
    }
}
