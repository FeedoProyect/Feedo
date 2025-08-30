package com.benjamin.proyectofeedo.core

import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.apiService.ComidasApiService
import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.apiService.ComidasFavoritosApiService
import com.benjamin.proyectofeedo.pantallasPrincipales.data.SupaBase.SupabaseAuthInterceptor
import com.benjamin.proyectofeedo.pantallasPrincipales.data.repositoriosImpl.FavoritosRepositoryImpl
import com.benjamin.proyectofeedo.pantallasPrincipales.data.repositoriosImpl.RepositoryImpl
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.repositorios.FavoritosRepository
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.repositorios.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModul {

    private const val BASE_URL = "https://vdtfvlfmdurwsdcvircw.supabase.co/rest/v1/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(SupabaseAuthInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideComidasApiService(retrofit: Retrofit): ComidasApiService {
        return retrofit.create(ComidasApiService::class.java)
    }

    @Provides
    fun provideRepository(apiService: ComidasApiService): Repository {
        return RepositoryImpl(apiService)
    }

    @Provides
    fun provideComidasFavoritasApiService(retrofit: Retrofit): ComidasFavoritosApiService {
        return retrofit.create(ComidasFavoritosApiService::class.java)
    }

    @Provides
    fun provideFavoritosRepository(favoritosApiService: ComidasFavoritosApiService): FavoritosRepository {
        return FavoritosRepositoryImpl(favoritosApiService)
    }
}