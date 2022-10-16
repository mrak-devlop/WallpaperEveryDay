package ru.kitfactory.wallpapereveryday.di

import dagger.Component
import ru.kitfactory.wallpapereveryday.App
import ru.kitfactory.wallpapereveryday.di.module.AppModule
import ru.kitfactory.wallpapereveryday.di.module.DataModule
import ru.kitfactory.wallpapereveryday.di.module.UtilModule
import ru.kitfactory.wallpapereveryday.ui.listwallpaperfragment.ListWallpaperFragment
import ru.kitfactory.wallpapereveryday.ui.settingsFragment.SettingsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, UtilModule::class])
interface AppComponent {
    fun inject(listWallpaperFragment: ListWallpaperFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun injectTo(application: App)
}
