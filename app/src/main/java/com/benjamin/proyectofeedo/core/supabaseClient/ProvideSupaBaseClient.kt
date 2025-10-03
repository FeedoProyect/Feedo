package com.benjamin.proyectofeedo.core.supabaseClient

import com.benjamin.proyectofeedo.PantallasPrincipales.data.repositoriosImpl.AddComidaRepositoryImpl
import com.benjamin.proyectofeedo.PantallasPrincipales.data.repositoriosImpl.RepositoryImpl
import com.benjamin.proyectofeedo.PantallasPrincipales.data.repositoriosImpl.UserInformationRepositoryImpl
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.AddComidaRepository
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.Repository
import com.benjamin.proyectofeedo.PantallasPrincipales.domain.repositorios.UserInformationRepository
import com.benjamin.proyectofeedo.usuarioLogin.LoginData.AuthRepositoryImpl
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.FlowType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object ProvideSupaBaseClient {

    private const val BASE_URL = "https://vdtfvlfmdurwsdcvircw.supabase.co"
    private const val SUPABASE_ANON_KEY =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZkdGZ2bGZtZHVyd3NkY3ZpcmN3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTMyODk3MzEsImV4cCI6MjA2ODg2NTczMX0.tHJ0zc7ZVsC_sxXGISisq7y_wxT-PWYH4UHSjS6iuos"

    // 🔹 Cliente principal de Supabase
    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = BASE_URL,
            supabaseKey = SUPABASE_ANON_KEY
        ) {
            install(Postgrest)
            install(Auth) {
                flowType = FlowType.PKCE
                scheme = "app"
                host = "supabase.com"
            }
            install(Storage) // ✅ instalamos módulo de Storage
        }
    }

    @Provides
    @Singleton
    fun provideSupabaseDatabase(client: SupabaseClient): Postgrest {
        return client.postgrest
    }

    @Provides
    @Singleton
    fun provideSupabaseAuth(client: SupabaseClient): Auth {
        return client.auth
    }

    @Provides
    @Singleton
    fun provideSupabaseStorage(client: SupabaseClient): Storage {
        return client.storage
    }

    @Provides
    @Singleton
    fun provideAuthRepository(auth: Auth, client: SupabaseClient): AuthRepository {
        return AuthRepositoryImpl(auth, client)
    }

    @Provides
    @Singleton
    fun provideRepository(client: SupabaseClient): Repository {
        return RepositoryImpl(client)
    }

    // 🔹 Ahora este repo recibe el Storage además del Client
    @Provides
    @Singleton
    fun provideUserInformationRepository(
        client: SupabaseClient,
        storage: Storage
    ): UserInformationRepository {
        return UserInformationRepositoryImpl(client, storage)
    }

    @Provides
    @Singleton
    fun provideAddComidaRepository(client: SupabaseClient): AddComidaRepository {
        return AddComidaRepositoryImpl(client)
    }
}


