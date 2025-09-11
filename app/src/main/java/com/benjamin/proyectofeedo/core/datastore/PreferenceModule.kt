package com.benjamin.proyectofeedo.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.benjamin.proyectofeedo.core.datastore.Local.DataStorePreferences
import com.benjamin.proyectofeedo.settingsFeedo.data.ThemeRepositoryImpl
import com.benjamin.proyectofeedo.settingsFeedo.domain.ThemeRepository
import com.benjamin.proyectofeedo.usuarioLogin.LoginData.AuthRepositoryImpl
import com.benjamin.proyectofeedo.usuarioLogin.LoginData.SessionRepositoryImpl
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.AuthRepository
import com.benjamin.proyectofeedo.usuarioLogin.LoginDomain.SessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.auth.Auth
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    private const val PREFERENCES_NAME = "app_prefs"

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(PREFERENCES_NAME) }
        )
    }

    @Provides
    @Singleton
    fun provideDataStorePreferences(
        dataStore: DataStore<Preferences>
    ): DataStorePreferences {
        return DataStorePreferences(dataStore)
    }

    @Provides
    @Singleton
    fun provideThemeRepository(
        dataStorePreferences: DataStorePreferences
    ): ThemeRepository {
        return ThemeRepositoryImpl(dataStorePreferences)
    }

    @Provides
    @Singleton
    fun provideSessionRepository(
        dataStorePreference: DataStorePreferences
    ) : SessionRepository {
        return SessionRepositoryImpl(dataStorePreference)
    }
}