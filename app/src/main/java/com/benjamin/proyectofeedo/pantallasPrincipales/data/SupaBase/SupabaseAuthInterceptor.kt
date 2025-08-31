package com.benjamin.proyectofeedo.pantallasPrincipales.data.SupaBase

import io.github.jan.supabase.auth.Auth
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class SupabaseAuthInterceptor( private val auth: Auth): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val session = runBlocking { auth.currentSessionOrNull() }
        val token = session?.accessToken

        val request = if (!token.isNullOrEmpty()){
            chain.request().newBuilder()
                .addHeader("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZkdGZ2bGZtZHVyd3NkY3ZpcmN3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTMyODk3MzEsImV4cCI6MjA2ODg2NTczMX0.tHJ0zc7ZVsC_sxXGISisq7y_wxT-PWYH4UHSjS6iuos")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InZkdGZ2bGZtZHVyd3NkY3ZpcmN3Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTMyODk3MzEsImV4cCI6MjA2ODg2NTczMX0.tHJ0zc7ZVsC_sxXGISisq7y_wxT-PWYH4UHSjS6iuos")
                .build()
        } else {
            chain.request()
        }
        return chain.proceed(request)
    }
}