package ru.kitfactory.wallpapereveryday.workmanager

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kitfactory.wallpapereveryday.data.repository.WallpaperRepository


class DeleteOldImage(private val repository: WallpaperRepository, private val date: String) {

    suspend fun execute(){
        withContext(Dispatchers.IO) {
            val wallpapers = repository.getListWallpaper()
            wallpapers.forEach {
                val endDate = it.endDate
                if (date == endDate){
                    repository.removeWallpaper(it)
                }

            }
        }
        Log.i("wallpaper_debug", "DeleteOldImage")

    }

}