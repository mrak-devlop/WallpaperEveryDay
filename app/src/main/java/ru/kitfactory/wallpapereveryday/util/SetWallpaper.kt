package ru.kitfactory.wallpapereveryday.util

import android.app.WallpaperManager
import android.content.Context
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SetWallpaper(context: Context, imageView: ImageView) {
    private val manager = WallpaperManager.getInstance(context)
    private val image = imageView

    suspend fun applyForAllScreen(){
        withContext(Dispatchers.IO) {
            manager.setBitmap(image.drawable.toBitmap())
        }
    }
    suspend fun applyForLockScreen(){
        withContext(Dispatchers.IO) {
        manager.setBitmap(
            image.drawable.toBitmap(),
            null,
            false,
            WallpaperManager.FLAG_LOCK
        )
        }
    }
    suspend fun applyForHomeScreen(){
        withContext(Dispatchers.IO) {
        manager.setBitmap(
            image.drawable.toBitmap(),
            null,
            false,
            WallpaperManager.FLAG_SYSTEM
        )
        }
    }
}