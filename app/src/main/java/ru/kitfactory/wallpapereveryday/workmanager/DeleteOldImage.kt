package ru.kitfactory.wallpapereveryday.workmanager

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kitfactory.wallpapereveryday.data.repository.WallpaperRepository
import java.text.SimpleDateFormat
import java.util.*


class DeleteOldImage(private val repository: WallpaperRepository) {
    private val inFormat = SimpleDateFormat("dd.MM.uu", Locale.US)

    suspend fun execute(){
        withContext(Dispatchers.IO) {
            val lastWallpaper = repository.getLastWallpaper("6")
            val lastWeekWallpaperDate = inFormat.parse(lastWallpaper.startDate)
            val wallpapers = repository.getListWallpaper()
            wallpapers.forEach {
                val currentWallpaperDate: Date = inFormat.parse(it.startDate) as Date
                if (currentWallpaperDate.before(lastWeekWallpaperDate)){
                    repository.removeWallpaper(it)
                }

            }
        }
        Log.i("wallpaper_debug", "DeleteOldImage")

    }

}