package ru.kitfactory.wallpapereveryday.workmanager

import android.util.Log
import ru.kitfactory.wallpapereveryday.domain.WallpaperRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class DeleteOldImage(private val repository: WallpaperRepository) {


    suspend fun execute() {
        val inFormat = DateTimeFormatter.ofPattern("dd.MM.uu")
        val weekData = LocalDate
            .now().minusWeeks(1)
            val wallpapers = repository.getListWallpaper()
            for (count in wallpapers) {
                val currentWallpaperDate = LocalDate.parse(count.startDate, inFormat)
                if (weekData > currentWallpaperDate){
                    repository.removeWallpaper(count)
                }
            }

        Log.i("wallpaper_debug", "DeleteOldImage")
    }

}