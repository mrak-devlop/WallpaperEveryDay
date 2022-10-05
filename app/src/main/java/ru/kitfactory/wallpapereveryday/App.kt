package ru.kitfactory.wallpapereveryday

import ru.kitfactory.wallpapereveryday.di.DaggerAppComponent
import javax.inject.Inject


import android.app.Application
import android.util.Log
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kitfactory.wallpapereveryday.di.AppComponent
import ru.kitfactory.wallpapereveryday.di.factory.GetLastWallpaperWorkerFactory
import ru.kitfactory.wallpapereveryday.di.module.DataModule
import ru.kitfactory.wallpapereveryday.workmanager.GetLastWallpaperWorker
import java.util.concurrent.TimeUnit


class App : Application() {
@Inject
lateinit var getLastWallpaperWorkerFactory: GetLastWallpaperWorkerFactory
    companion object {
        lateinit var instance: App
    }
    lateinit var appComponent: AppComponent
    private val applicationScope = CoroutineScope(Dispatchers.IO)
    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent
            .builder()
            .dataModule(DataModule(this))
            .build()
        appComponent.injectTo(this)
        val workManagerConfig = Configuration
            .Builder()
            .setWorkerFactory(getLastWallpaperWorkerFactory)
            .build()
        WorkManager.initialize(this, workManagerConfig)
        delayedInit()

    }

    private fun delayedInit() = applicationScope.launch {
        Log.i("worker_log", "applicationScope run")
        scheduleGetLastWallpaper()
    }

    private fun scheduleGetLastWallpaper() {
        val repeatRequest =
            PeriodicWorkRequestBuilder<GetLastWallpaperWorker>(
                16,
                TimeUnit.MINUTES,
                1,
                TimeUnit.MINUTES
            )
                .setConstraints(makeConstraints())
                .build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            GetLastWallpaperWorker.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, repeatRequest
        )
    }

    private fun makeConstraints(): Constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

}