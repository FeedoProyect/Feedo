package com.benjamin.proyectofeedo.settingsFeedo.data.Local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.benjamin.proyectofeedo.settingsFeedo.data.PreferenceRepositoryImpl
import com.benjamin.proyectofeedo.settingsFeedo.domain.PreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    private const val PREFERENCES_NAME = "settings_prefs"

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
    fun providePreferenceRepository(
        dataStorePreferences: DataStorePreferences
    ): PreferenceRepository {
        return PreferenceRepositoryImpl(dataStorePreferences)
    }
}