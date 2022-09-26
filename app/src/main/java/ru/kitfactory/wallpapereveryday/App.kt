package ru.kitfactory.wallpapereveryday


import android.app.Application
import ru.kitfactory.wallpapereveryday.di.AppComponent
import ru.kitfactory.wallpapereveryday.di.DaggerAppComponent
import ru.kitfactory.wallpapereveryday.di.module.DataModule


class App : Application() {
    companion object {
        lateinit var instance: App
    }
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerAppComponent
            .builder()
            .dataModule(DataModule(this))
            .build()
    }
}