package ru.kitfactory.wallpapereveryday.data.database


import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kitfactory.wallpapereveryday.domain.Wallpaper

@Entity(tableName = "wallpaper_table")
data class LocalDbWallpaper constructor(
    val copyright: String,
    val copyrightLink: String,
    val endDate: String,
    @PrimaryKey
    val startDate: String,
    val url: String
)

fun List<LocalDbWallpaper>.asDomainModel(): List<Wallpaper> {
    return map {
        Wallpaper(
            copyright = it.copyright,
            copyrightLink = it.copyrightLink,
            endDate = it.endDate,
            startDate = it.startDate,
            url = it.url
        )
    }
}