package com.benjamin.proyectofeedo.data.Network

import com.benjamin.proyectofeedo.data.RepositoryImpl
import com.benjamin.proyectofeedo.data.SupaBase.SupabaseAuthInterceptor
import com.benjamin.proyectofeedo.domain.Repository
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
    fun provideComidasApiService(retrofit: Retrofit): ComidasApiService{
        return retrofit.create(ComidasApiService::class.java)
    }

    @Provides
    fun provideRepository(apiService: ComidasApiService): Repository{
        return RepositoryImpl(apiService)
    }
}