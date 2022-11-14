package ru.kitfactory.wallpapereveryday.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kitfactory.wallpapereveryday.data.repository.WallpaperRepositoryImpl
import ru.kitfactory.wallpapereveryday.utility.SetWallpaper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class GetLastWallpaperWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val repository: WallpaperRepositoryImpl,
) : CoroutineWorker(context, workerParams) {
    companion object {
        const val LAST_WALLPAPER = "0"
        const val WORK_NAME = "UPDATE_WALLPAPER"
        private const val UPDATE_WALLPAPER_TYPE = "UPDATE_WALLPAPER_TYPE"
        private const val ALL_SCREEN = "ALL"
        private const val LOCK_SCREEN = "LOCK"
        private const val HOME_SCREEN = "HOME"
        private const val DELETE_OLD = "DELETE_OLD"
        private const val LAST_UPDATE = "UPDATE"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
            try {
                val lastUpdate = repository.getPreferences(LAST_UPDATE)
                val date = LocalDateTime
                    .now()
                    .format(DateTimeFormatter.ofPattern("dd.MM.uu"))

                if (date != lastUpdate ) {
                    repository.addNewWallpaper(LAST_WALLPAPER)
                    val wallpaper = repository.getLastWallpaper(date)

                    val installWallpaperSettings = repository.getPreferences(WORK_NAME).toBoolean()
                    if (installWallpaperSettings) {

                        val bitmap = Glide
                            .with(context.applicationContext)
                            .asBitmap()
                            .centerCrop()
                            .load(wallpaper.url)
                            .submit()
                            .get()

                        when (repository.getPreferences(UPDATE_WALLPAPER_TYPE)) {
                            ALL_SCREEN -> SetWallpaper(context.applicationContext)
                                .applyForAllScreen(bitmap)
                            LOCK_SCREEN -> SetWallpaper(context.applicationContext)
                                .applyForLockScreen(bitmap)
                            HOME_SCREEN -> SetWallpaper(context.applicationContext)
                                .applyForHomeScreen(bitmap)
                        }
                        repository.addPreferences(LAST_UPDATE, date)
                    }
                }

                val deletePrefs = repository.getPreferences(DELETE_OLD).toBoolean()
                if (deletePrefs) {
                    DeleteOldImage(repository).execute()
                }

                Result.success()
            } catch (e: Exception) {
                Result.retry()
            }
    }


    @AssistedFactory
    interface Factory {
        fun create(appContext: Context, params: WorkerParameters): GetLastWallpaperWorker
    }
}