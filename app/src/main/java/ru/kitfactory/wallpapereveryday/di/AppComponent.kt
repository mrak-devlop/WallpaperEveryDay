package ru.kitfactory.wallpapereveryday.di

import dagger.Component
import ru.kitfactory.wallpapereveryday.di.module.AppModule
import ru.kitfactory.wallpapereveryday.di.module.DataModule
import ru.kitfactory.wallpapereveryday.ui.listwallpaperfragment.ListWallpaperFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {
    fun inject(listWallpaperFragment: ListWallpaperFragment)
}
