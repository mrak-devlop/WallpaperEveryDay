package ru.kitfactory.wallpapereveryday.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.Date

@Dao
interface DaoDatabase {
    @Query("SELECT * FROM wallpaper_table ORDER BY startDate ASC")
    fun getWallpapers(): LiveData<List<LocalDbWallpaper>>

    @Query("SELECT * FROM wallpaper_table WHERE startDate=(:startDate)")
    fun getLastWallpaper(startDate: String): LocalDbWallpaper

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallpaper(wallpaper: LocalDbWallpaper)

    @Delete
    suspend fun removePaint(wallpaper: LocalDbWallpaper)
}