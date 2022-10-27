package ru.kitfactory.wallpapereveryday.utility

import android.content.Context

class ScreenGrid(private val context: Context, private val columnWidthDp: Float) {
    fun calculate(): Int {
        val displayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }
}