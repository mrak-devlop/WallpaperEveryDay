package ru.kitfactory.wallpapereveryday.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LocalDbWallpaper::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract val daoDatabase: DaoDatabase
}