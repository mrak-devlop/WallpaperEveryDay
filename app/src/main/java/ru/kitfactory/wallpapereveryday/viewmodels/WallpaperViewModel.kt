package ru.kitfactory.wallpapereveryday.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kitfactory.wallpapereveryday.domain.WallpaperRepository
import ru.kitfactory.wallpapereveryday.utility.SetWallpaper

class WallpaperViewModel(private val repository: WallpaperRepository,
                         private val setWallpaper: SetWallpaper
): ViewModel() {
    companion object {
        private const val UPDATE_WALLPAPER = "UPDATE_WALLPAPER"
        private const val ALL_SCREEN = "ALL"
        private const val LOCK_SCREEN = "LOCK"
        private const val HOME_SCREEN = "HOME"
        private const val UPDATE_WALLPAPER_TYPE = "UPDATE_WALLPAPER_TYPE"
    }

    private fun setUpdateWallpaperForAllScreen() {
        repository.addPreferences(UPDATE_WALLPAPER_TYPE, ALL_SCREEN)
    }

    private fun setUpdateWallpaperForLockScreen() {
        repository.addPreferences(UPDATE_WALLPAPER_TYPE, LOCK_SCREEN)
    }

    private fun setUpdateWallpaperForHomeScreen() {
        repository.addPreferences(UPDATE_WALLPAPER_TYPE, HOME_SCREEN)
    }

    fun setWallpaperForAllScreen(bitmap: Bitmap){
        viewModelScope.launch(Dispatchers.IO) {
            setWallpaper.applyForAllScreen(bitmap)
            setUpdateWallpaperForAllScreen()
        }

    }

    fun setWallpaperForLockScreen(bitmap: Bitmap){
        viewModelScope.launch(Dispatchers.IO) {
            setWallpaper.applyForLockScreen(bitmap)
            setUpdateWallpaperForLockScreen()
        }

    }

    fun setWallpaperForHomeScreen(bitmap: Bitmap){
        viewModelScope.launch(Dispatchers.IO) {
            setWallpaper.applyForHomeScreen(bitmap)
            setUpdateWallpaperForHomeScreen()
        }

    }
    fun setAutoUpdate() {
        repository.addPreferences(UPDATE_WALLPAPER, "true")
    }
    fun removeAutoUpdate() {
        repository.addPreferences(UPDATE_WALLPAPER, "false")
    }

}