package ru.kitfactory.wallpapereveryday.data.network

import retrofit2.Response
import ru.kitfactory.wallpapereveryday.data.database.LocalDbWallpaper
import ru.kitfactory.wallpapereveryday.domain.Wallpaper
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NetDataTransfer {
    fun bingWallpaperToDatabaseModel(wallpaperResponse: Response<BingWallpaper>): LocalDbWallpaper {
        val wallpaper = wallpaperResponse.body()!!
        return LocalDbWallpaper(
            copyright = wallpaper.copyright,
            copyrightLink = wallpaper.copyrightLink,
            endDate = parseData(wallpaper.endDate),
            startDate = parseData(wallpaper.startDate),
            url = wallpaper.url
        )

    }

    fun bingWallpaperToDomainModel(wallpaperResponse: Response<BingWallpaper>): Wallpaper {
        val wallpaper = wallpaperResponse.body()!!
        return Wallpaper(
            copyright = wallpaper.copyright,
            copyrightLink = wallpaper.copyrightLink,
            endDate = wallpaper.endDate,
            startDate = wallpaper.startDate,
            url = wallpaper.url
        )

    }

    private fun parseData(data:String):String {
        val inFormat = DateTimeFormatter.ofPattern("uuuuMMdd")
        val outFormat = DateTimeFormatter.ofPattern("dd.MM.uu")
        return LocalDate.parse(data,inFormat).format(outFormat)
    }


}