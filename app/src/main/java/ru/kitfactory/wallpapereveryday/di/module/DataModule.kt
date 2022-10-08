package ru.kitfactory.wallpapereveryday.di.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.kitfactory.wallpapereveryday.data.database.LocalDatabase
import ru.kitfactory.wallpapereveryday.data.repository.WallpaperRepository
import ru.kitfactory.wallpapereveryday.data.storage.PreferencesStorage
import javax.inject.Singleton

@Module
class DataModule() {
    private lateinit var database: LocalDatabase
    @Singleton
    @Provides
    fun provideLocalDatabase(application: Application): LocalDatabase {
        database = Room
            .databaseBuilder(application,
                LocalDatabase::class.java,
                "wallpaper_database")
            .build()
        return database
    }
    @Singleton
    @Provides
    fun provideWallpaperRepository(database: LocalDatabase): WallpaperRepository {
        return WallpaperRepository(database = database)
    }
    @Provides
    fun providePreferencesStorage(application: Application): PreferencesStorage {
        return PreferencesStorage(application)
    }
}