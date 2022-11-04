package ru.kitfactory.wallpapereveryday.domain

import androidx.lifecycle.LiveData

interface WallpaperRepository {
    val wallpapers: LiveData<List<Wallpaper>>

    suspend fun removeWallpaper(wallpaper: Wallpaper)

    suspend fun getListWallpaper(): List<Wallpaper>

    suspend fun addNewWallpaper(index: String)

    fun getPreferences(name: String): String

    fun addPreferences(name: String, value: String)

    fun getLastWallpaper(startDate: String): Wallpaper



}