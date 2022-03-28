package com.example.githubuser.utils

import android.content.Context
import android.content.SharedPreferences

object PrefManager {
    private const val PREF_NAME = "github-user-database"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val isDarkMode = Pair("isDarkMode", false)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(
            PREF_NAME,
            MODE
        )
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var IS_DARK_MODE: Boolean
        get() = preferences.getBoolean(
            isDarkMode.first, isDarkMode.second
        )
        set(value) = preferences.edit {
            it.putBoolean(isDarkMode.first, value)
        }

}