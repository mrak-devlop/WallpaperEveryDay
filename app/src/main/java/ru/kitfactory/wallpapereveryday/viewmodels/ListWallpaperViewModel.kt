package ru.kitfactory.wallpapereveryday.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kitfactory.wallpapereveryday.data.repository.WallpaperRepository
import ru.kitfactory.wallpapereveryday.domain.Wallpaper

class ListWallpaperViewModel(private val repository: WallpaperRepository) : ViewModel() {

    val localWallpaper: LiveData<List<Wallpaper>> = repository.wallpapers

    fun loadWallpapersOnWeekInDb() {
        for (index in 0..6) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addNewWallpaper(index.toString())
            }
        }

    }
}