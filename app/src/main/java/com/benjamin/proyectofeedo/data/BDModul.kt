package com.benjamin.proyectofeedo.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.network.SupabaseHttpClient
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BDModul {

    @Provides
    @Singleton
    fun provideGetClientSupabase(): SupabaseClient{
        return createSupabaseClient(
            supabaseUrl = "https://vdtfvlfmdurwsdcvircw.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZkdGZ2bGZtZHVyd3NkY3ZpcmN3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTMyODk3MzEsImV4cCI6MjA2ODg2NTczMX0.tHJ0zc7ZVsC_sxXGISisq7y_wxT-PWYH4UHSjS6iuos",
        ){
            install(Postgrest)
        }
    }
}