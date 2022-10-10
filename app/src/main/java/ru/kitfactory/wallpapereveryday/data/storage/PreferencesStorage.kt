package ru.kitfactory.wallpapereveryday.data.storage

import android.content.Context
import android.content.SharedPreferences

const val STORAGE_NAME = "Storage"

class PreferencesStorage(context: Context) {
    private val settings: SharedPreferences = context
        .getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = settings.edit()

    fun addProperty(name: String, value: String) {
        editor.putString(name, value)
        editor.apply()
    }

    fun getProperty(name: String): String {
        return settings.getString(name, "none").toString()
    }

}