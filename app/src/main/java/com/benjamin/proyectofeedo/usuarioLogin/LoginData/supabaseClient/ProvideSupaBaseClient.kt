package com.benjamin.proyectofeedo.usuarioLogin.LoginData.supabaseClient

import com.benjamin.proyectofeedo.usuarioLogin.LoginData.AuthRepositoryImpl
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvideSupaBaseClient {

    private const val BASE_URL = "https://vdtfvlfmdurwsdcvircw.supabase.co"
    private const val SUPABASE_ANON_KEY =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZkdGZ2bGZtZHVyd3NkY3ZpcmN3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTMyODk3MzEsImV4cCI6MjA2ODg2NTczMX0.tHJ0zc7ZVsC_sxXGISisq7y_wxT-PWYH4UHSjS6iuos"

    @Provides
    @Singleton
    fun metodoProvideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = BASE_URL,
            supabaseKey = SUPABASE_ANON_KEY
        ) {
            install(Auth)
        }
    }

    @Provides
    @Singleton
    fun provideAuthRepository(client: SupabaseClient): AuthRepository {
        return AuthRepositoryImpl(client)
    }
}