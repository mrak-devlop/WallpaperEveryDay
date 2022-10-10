package ru.kitfactory.wallpapereveryday.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kitfactory.wallpapereveryday.data.repository.WallpaperRepository
import ru.kitfactory.wallpapereveryday.util.SetWallpaper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class GetLastWallpaperWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val repository: WallpaperRepository,
) : CoroutineWorker(context, workerParams) {
    companion object {
        const val LAST_WALLPAPER = "0"
        const val WORK_NAME = "UPDATE_WALLPAPER"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            repository.addNewWallpaper(LAST_WALLPAPER)
            val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.uu"))
            Log.i("wallpaper_debug", date.toString())
            val wallpaper = repository.getLastWallpaper(date)
            Log.i("wallpaper_debug", "Run Glide")
            val bitmap = Glide
                .with(context.applicationContext)
                .asBitmap()
                .centerCrop()
                .load(wallpaper.url)
                .submit()
                .get()
            Log.i("wallpaper_debug", "setWallpaper ")
            SetWallpaper(context.applicationContext).applyForAllScreen(bitmap)
            Log.i("wallpaper_debug", "Success")
            Result.success()
        } catch (e: Exception) {
            Log.i("wallpaper_debug", "Retry")
            Result.retry()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(appContext: Context, params: WorkerParameters): GetLastWallpaperWorker
    }
}