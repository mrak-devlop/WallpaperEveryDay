package ru.kitfactory.wallpapereveryday.domain

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Wallpaper(
    val copyright: String,
    val copyrightLink: String,
    val endDate: String,
    val startDate: String,
    val url: String
): Parcelable
