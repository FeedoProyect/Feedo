package com.benjamin.proyectofeedo.core.retrofit
import com.benjamin.proyectofeedo.core.SupaBaseRetrofit.SupabaseAuthInterceptor
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
}
