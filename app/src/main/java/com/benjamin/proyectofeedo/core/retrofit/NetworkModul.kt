package com.benjamin.proyectofeedo.core.retrofit

import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.apiService.ComidasApiService
import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.apiService.AddComidasApiService
import com.benjamin.proyectofeedo.pantallasPrincipales.data.Network.apiService.UserInformationApiService
import com.benjamin.proyectofeedo.pantallasPrincipales.data.SupaBase.SupabaseAuthInterceptor
import com.benjamin.proyectofeedo.pantallasPrincipales.data.repositoriosImpl.AddComidaRepositoryImpl
import com.benjamin.proyectofeedo.pantallasPrincipales.data.repositoriosImpl.RepositoryImpl
import com.benjamin.proyectofeedo.pantallasPrincipales.data.repositoriosImpl.UserInformationRepositoryImpl
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.repositorios.AddComidaRepository
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.repositorios.Repository
import com.benjamin.proyectofeedo.pantallasPrincipales.domain.repositorios.UserInformationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.auth.Auth
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
    fun provideOkHttpClient(auth: Auth): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(SupabaseAuthInterceptor(auth))
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
    fun provideAddComidaApiService(retrofit: Retrofit): AddComidasApiService {
        return retrofit.create(AddComidasApiService::class.java)
    }

    @Provides
    fun provideAddComidaRepository(addComidas: AddComidasApiService): AddComidaRepository {
        return AddComidaRepositoryImpl(addComidas)
    }

    @Provides
    fun provideUserInformationApiService(retrofit: Retrofit): UserInformationApiService {
        return retrofit.create(UserInformationApiService::class.java)
    }

    @Provides
    fun provideUserInformationRepository(userInformation: UserInformationApiService): UserInformationRepository {
        return UserInformationRepositoryImpl(userInformation)
    }
}