package ru.kitfactory.wallpapereveryday.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface BingService {
    @GET
    suspend fun fetchBingItem(@Url url: String): Response<BingWallpaper>

}
