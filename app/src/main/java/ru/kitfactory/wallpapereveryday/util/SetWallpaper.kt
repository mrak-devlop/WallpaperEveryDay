package ru.kitfactory.wallpapereveryday.util

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SetWallpaper(context: Context) {
    private val manager = WallpaperManager.getInstance(context)

    suspend fun applyForAllScreen(bitmap: Bitmap) {
        withContext(Dispatchers.IO) {
            manager.setBitmap(bitmap)
        }
    }

    suspend fun applyForLockScreen(bitmap: Bitmap) {
        withContext(Dispatchers.IO) {
            manager.setBitmap(
                bitmap,
                null,
                false,
                WallpaperManager.FLAG_LOCK
            )
        }
    }

    suspend fun applyForHomeScreen(bitmap: Bitmap) {
        withContext(Dispatchers.IO) {
            manager.setBitmap(
                bitmap,
                null,
                false,
                WallpaperManager.FLAG_SYSTEM
            )
        }
    }
}