package ru.kitfactory.wallpapereveryday.viewmodels

import androidx.lifecycle.ViewModel
import ru.kitfactory.wallpapereveryday.data.repository.WallpaperRepository

class SettingsViewModel(private val repository: WallpaperRepository): ViewModel() {

    companion object {
        private const val UPDATE_WALLPAPER = "UPDATE_WALLPAPER"
        private const val DELETE_OLD = "DELETE_OLD"
    }

    private fun loadSettings(name: String): String {
        return repository.getPreferences(name)
    }

    private fun saveSettings(name: String, value: String){
        repository.addPreferences(name, value)
    }

    fun setUpdateWallpaperSettings(state: Boolean){
        if (state){
            saveSettings(UPDATE_WALLPAPER, state.toString())
        } else {
            saveSettings(UPDATE_WALLPAPER, state.toString())
        }

    }

    fun setDeleteOldWallpaperSettings(state: Boolean) {
        if (state){
            saveSettings(DELETE_OLD, state.toString())
        } else {
            saveSettings(DELETE_OLD, state.toString())
        }
    }

    fun loadUpdateWallpaperSettings(): Boolean{
        val result = loadSettings(UPDATE_WALLPAPER)
        return result.toBoolean()
    }

    fun loadDeleteOldWallpaperSettings(): Boolean{
        val result = loadSettings(DELETE_OLD)
        return result.toBoolean()
    }

}