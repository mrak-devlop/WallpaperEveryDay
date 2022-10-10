package ru.kitfactory.wallpapereveryday.di.module

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule(application: Application) {
    private val mainApplication = application

    @Provides
    fun provideAppApplication(): Application {
        return mainApplication
    }
}