package ru.kitfactory.wallpapereveryday.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.kitfactory.wallpapereveryday.data.database.LocalDbWallpaper
import ru.kitfactory.wallpapereveryday.data.database.asDomainModel
import ru.kitfactory.wallpapereveryday.data.network.BingNetwork
import ru.kitfactory.wallpapereveryday.data.network.NetDataTransfer

interface WallpaperRepository {
    val wallpapers: LiveData<List<Wallpaper>>

    suspend fun removeWallpaper(wallpaper: Wallpaper)

    suspend fun getListWallpaper(): List<Wallpaper>

    suspend fun addNewWallpaper(index: String)

    fun getPreferences(name: String): String

    fun addPreferences(name: String, value: String)

    fun getLastWallpaper(startDate: String): Wallpaper



}