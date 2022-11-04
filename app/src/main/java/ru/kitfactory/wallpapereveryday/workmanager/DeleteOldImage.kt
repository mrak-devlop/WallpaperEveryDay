package ru.kitfactory.wallpapereveryday.workmanager

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kitfactory.wallpapereveryday.data.repository.WallpaperRepositoryImpl
import ru.kitfactory.wallpapereveryday.domain.WallpaperRepository
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


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