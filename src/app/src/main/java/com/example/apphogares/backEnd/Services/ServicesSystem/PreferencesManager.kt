package com.example.apphogares.backEnd.Services.ServicesSystem

import android.content.Context
import android.content.SharedPreferences
import com.example.apphogares.AppHogaresAplication
import javax.inject.Inject

class  PreferencesManager @Inject constructor() {
    val context = AppHogaresAplication.context
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getData(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
}