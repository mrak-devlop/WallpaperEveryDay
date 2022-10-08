package ru.kitfactory.wallpapereveryday.workmanager

import android.content.Context
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
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

class GetLastWallpaperWorker @AssistedInject constructor (
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val repository: WallpaperRepository,
    private val setWallpaper: SetWallpaper
) : CoroutineWorker(context, workerParams) {
    private lateinit var imageView: ImageView
    companion object{
        const val LAST_WALLPAPER = "0"
        const val WORK_NAME = "UPDATE_WALLPAPER"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            repository.addNewWallpaper(LAST_WALLPAPER)
            val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.uu"))
            val wallpaper = repository.getLastWallpaper(date)
            Glide
                .with(context)
                .load(wallpaper.url)
                .centerCrop()
                .into(imageView)
            setWallpaper.applyForAllScreen(imageView.drawable.toBitmap())
            Glide.get(context).clearMemory()
            Result.success()
        }
        catch (e: Exception){
            Result.retry()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(appContext: Context, params: WorkerParameters): GetLastWallpaperWorker
    }
}