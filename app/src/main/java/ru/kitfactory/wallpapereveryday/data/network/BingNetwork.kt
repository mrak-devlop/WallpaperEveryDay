package ru.kitfactory.wallpapereveryday.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BingNetwork {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://bing.biturl.top")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val bing = retrofit.create(BingService::class.java)
}