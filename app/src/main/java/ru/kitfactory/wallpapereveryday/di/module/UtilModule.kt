package ru.kitfactory.wallpapereveryday.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import ru.kitfactory.wallpapereveryday.util.InternetConnection
import ru.kitfactory.wallpapereveryday.util.SetWallpaper
import javax.inject.Singleton

@Module
class UtilModule {
    @Provides
    fun provideSetWallpaper(application: Application): SetWallpaper {
        return SetWallpaper(application.applicationContext)
    }

    @Singleton
    @Provides
    fun provideInternetConnection(application: Application): InternetConnection {
        return InternetConnection(application)
    }
}