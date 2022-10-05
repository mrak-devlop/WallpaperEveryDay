package ru.kitfactory.wallpapereveryday.di.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.kitfactory.wallpapereveryday.workmanager.GetLastWallpaperWorker
import javax.inject.Inject

class GetLastWallpaperWorkerFactory @Inject constructor(
    private val getLastWallpaperWorkerFactory: GetLastWallpaperWorker.Factory
    ): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            GetLastWallpaperWorker::class.java.name ->
               getLastWallpaperWorkerFactory.create(appContext, workerParameters)
            else -> null
        }
    }
}