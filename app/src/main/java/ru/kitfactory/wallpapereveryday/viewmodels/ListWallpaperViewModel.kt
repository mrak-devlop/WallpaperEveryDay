package ru.kitfactory.wallpapereveryday.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kitfactory.wallpapereveryday.data.repository.WallpaperRepository
import ru.kitfactory.wallpapereveryday.domain.Wallpaper

class ListWallpaperViewModel(private val repository: WallpaperRepository) : ViewModel() {
    companion object{
        private const val UPDATE_WALLPAPER = "UPDATE_WALLPAPER"
        private const val DELETE_OLD = "DELETE_OLD"
        private const val FIRST_RUN = "FIRST_RUN"
    }

    val localWallpaper: LiveData<List<Wallpaper>> = repository.wallpapers

    private fun loadWallpapersOnWeekInDb() {
        for (index in 0..6) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addNewWallpaper(index.toString())
            }
        }

    }

     fun checkFirstRun() {
             val state = repository.getPreferences(FIRST_RUN)
             if (state == "none") {
                 repository.addPreferences(FIRST_RUN, "NO")
                 repository.addPreferences(UPDATE_WALLPAPER, "true")
                 repository.addPreferences(DELETE_OLD, "true")
                 loadWallpapersOnWeekInDb()
             }
    }
}