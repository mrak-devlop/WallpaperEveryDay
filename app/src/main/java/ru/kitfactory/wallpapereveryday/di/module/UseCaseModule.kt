package ru.kitfactory.wallpapereveryday.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import ru.kitfactory.wallpapereveryday.utility.InternetConnection
import ru.kitfactory.wallpapereveryday.utility.SetWallpaper
import javax.inject.Singleton

@Module
class UseCaseModule {
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