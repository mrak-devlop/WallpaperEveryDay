package ru.kitfactory.wallpapereveryday.data.network

import com.google.gson.annotations.SerializedName


data class BingWallpaper(
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("copyright_link")
    val copyrightLink: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("url")
    val url: String
)

