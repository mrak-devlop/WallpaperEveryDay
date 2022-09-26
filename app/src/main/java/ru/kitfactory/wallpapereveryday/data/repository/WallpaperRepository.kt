package ru.kitfactory.wallpapereveryday.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kitfactory.wallpapereveryday.data.database.LocalDatabase
import ru.kitfactory.wallpapereveryday.data.database.asDomainModel
import ru.kitfactory.wallpapereveryday.data.network.BingNetwork
import ru.kitfactory.wallpapereveryday.data.network.NetDataTransfer
import ru.kitfactory.wallpapereveryday.domain.Wallpaper

class WallpaperRepository(private val database: LocalDatabase) {
    val wallpapers: LiveData<List<Wallpaper>> = Transformations.map(
        database.daoDatabase.getWallpapers()
    ) {
        it.asDomainModel()
    }

    suspend fun addNewWallpaper(index: String) {
        withContext(Dispatchers.IO) {
            val newWallpaper = BingNetwork.bing.fetchBingItem(
                "?resolution=1920" +
                "&format=json" +
                "&index=$index" +
                "&mkt=en-US"
            )
            database.daoDatabase.insertWallpaper(
                NetDataTransfer().bingWallpaperToDatabaseModel(newWallpaper)
            )
        }
    }




}