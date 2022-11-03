package ru.kitfactory.wallpapereveryday.workmanager

import android.util.Log
import ru.kitfactory.wallpapereveryday.data.repository.WallpaperRepositoryImpl
import java.text.SimpleDateFormat
import java.util.*


class DeleteOldImage(private val repository: WallpaperRepositoryImpl) {
    private val inFormat = SimpleDateFormat("dd.MM.uu", Locale.US)
    private val lastWallpaper = repository.getLastWallpaper("6")
    private val lastWallpaperDate = lastWallpaper.startDate
    private val lastWeekWallpaperDate = inFormat.parse(lastWallpaperDate) as Date

    suspend fun execute() {
            val wallpapers = repository.getListWallpaper()
            for (count in wallpapers) {
                val currentWallpaperDate = inFormat.parse(count.startDate) as Date
                    if (lastWeekWallpaperDate < currentWallpaperDate){
                        repository.removeWallpaper(count)
                    }
                }
        Log.i("wallpaper_debug", "DeleteOldImage")

    }

}