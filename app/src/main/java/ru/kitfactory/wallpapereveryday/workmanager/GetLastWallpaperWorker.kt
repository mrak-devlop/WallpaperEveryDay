package ru.kitfactory.wallpapereveryday.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kitfactory.wallpapereveryday.data.repository.WallpaperRepository


class GetLastWallpaperWorker @AssistedInject constructor (
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val repository: WallpaperRepository
) : CoroutineWorker(context, workerParams) {
    companion object{
        const val LAST_WALLPAPER = "0"
        const val WORK_NAME = "UPDATE_WALLPAPER"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        Log.i("worker_log", "coroutine run")
        try {
            repository.addNewWallpaper(LAST_WALLPAPER)
            Log.i("worker_log", "success")
            Result.success()
        }
        catch (e: Exception){
            Log.i("worker_log", "retry")
            Result.retry()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(appContext: Context, params: WorkerParameters): GetLastWallpaperWorker
    }
}