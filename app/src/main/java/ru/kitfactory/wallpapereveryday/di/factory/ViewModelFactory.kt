package ru.kitfactory.wallpapereveryday.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kitfactory.wallpapereveryday.data.repository.WallpaperRepository
import ru.kitfactory.wallpapereveryday.viewmodels.ListWallpaperViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor (private val repository: WallpaperRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListWallpaperViewModel::class.java)) {
            return ListWallpaperViewModel(repository) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}
