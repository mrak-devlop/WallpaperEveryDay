package ru.kitfactory.wallpapereveryday.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.kitfactory.wallpapereveryday.data.database.LocalDatabase
import ru.kitfactory.wallpapereveryday.data.database.LocalDbWallpaper
import ru.kitfactory.wallpapereveryday.data.database.asDomainModel
import ru.kitfactory.wallpapereveryday.data.network.BingNetwork
import ru.kitfactory.wallpapereveryday.data.network.NetDataTransfer
import ru.kitfactory.wallpapereveryday.data.storage.PreferencesStorage
import ru.kitfactory.wallpapereveryday.domain.Wallpaper

class WallpaperRepository(private val database: LocalDatabase,
                          private val preferences: PreferencesStorage) {

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
            delay(100)
        }
    }

    fun getPreferences(name: String): String {
            return preferences.getProperty(name)
    }

    fun addPreferences(name: String, value: String){
        preferences.addProperty(name, value)
    }


    fun getLastWallpaper(startDate: String): Wallpaper {
        val dbWallpaper = database.daoDatabase.getLastWallpaper(startDate)
        return dbWallpaperToDomainModel(dbWallpaper)
    }

    private fun dbWallpaperToDomainModel(dbWallpaper: LocalDbWallpaper): Wallpaper {
        return Wallpaper(
            copyright = dbWallpaper.copyright,
            copyrightLink = dbWallpaper.copyrightLink,
            endDate = dbWallpaper.endDate,
            startDate = dbWallpaper.startDate,
            url = dbWallpaper.url
        )

    }


}