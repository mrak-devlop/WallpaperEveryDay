package ru.kitfactory.wallpapereveryday.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kitfactory.wallpapereveryday.data.repository.WallpaperRepositoryImpl
import ru.kitfactory.wallpapereveryday.domain.WallpaperRepository
import ru.kitfactory.wallpapereveryday.utility.SetWallpaper
import ru.kitfactory.wallpapereveryday.viewmodels.ListWallpaperViewModel
import ru.kitfactory.wallpapereveryday.viewmodels.SettingsViewModel
import ru.kitfactory.wallpapereveryday.viewmodels.WallpaperViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(private val repository: WallpaperRepositoryImpl,
                                           private val setWallpaper: SetWallpaper
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListWallpaperViewModel::class.java)) {
            return ListWallpaperViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(WallpaperViewModel::class.java)){
            return WallpaperViewModel(repository, setWallpaper) as T
        }
        throw IllegalArgumentException("Unable to construct view-model")
    }
}
