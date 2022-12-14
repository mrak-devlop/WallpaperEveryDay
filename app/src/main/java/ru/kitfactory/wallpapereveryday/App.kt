package ru.kitfactory.wallpapereveryday


import android.app.Application
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kitfactory.wallpapereveryday.di.AppComponent
import ru.kitfactory.wallpapereveryday.di.DaggerAppComponent
import ru.kitfactory.wallpapereveryday.di.factory.GetLastWallpaperWorkerFactory
import ru.kitfactory.wallpapereveryday.di.module.AppModule
import ru.kitfactory.wallpapereveryday.workmanager.GetLastWallpaperWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject


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
            .appModule(AppModule(instance))
            .build()
        appComponent.injectTo(instance)
        val workManagerConfig = Configuration
            .Builder()
            .setWorkerFactory(getLastWallpaperWorkerFactory)
            .build()
        WorkManager.initialize(instance, workManagerConfig)
        delayedInit()

    }

    private fun delayedInit() = applicationScope.launch {
        scheduleGetLastWallpaper()
    }

    private fun scheduleGetLastWallpaper() {
        val repeatRequest =
            PeriodicWorkRequestBuilder<GetLastWallpaperWorker>(
                16,
                TimeUnit.MINUTES,
                5,
                TimeUnit.MINUTES
            )
                .setConstraints(makeConstraints())
                .build()
        WorkManager.getInstance(instance.applicationContext).enqueueUniquePeriodicWork(
            GetLastWallpaperWorker.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, repeatRequest
        )
    }

    private fun makeConstraints(): Constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

}