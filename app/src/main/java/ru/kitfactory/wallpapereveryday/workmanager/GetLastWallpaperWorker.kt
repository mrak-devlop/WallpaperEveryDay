package ru.kitfactory.wallpapereveryday.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kitfactory.wallpapereveryday.data.repository.WallpaperRepository
import javax.inject.Inject

class GetLastWallpaperWorker @Inject constructor (
    context: Context,
    workerParams: WorkerParameters,
    val repository: WallpaperRepository
) : CoroutineWorker(context, workerParams) {
    companion object{
        const val LAST_WALLPAPER = "0"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.Default) {
        Log.i("worker", "coroutine run")
        try {
            repository.addNewWallpaper(LAST_WALLPAPER)
            Log.i("worker", "success")
            Result.success()
        }
        catch (e: Exception){
            Log.i("worker", "retry")
            Result.retry()
        }
    }
}