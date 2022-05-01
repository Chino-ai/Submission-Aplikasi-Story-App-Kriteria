package com.example.submissionaplikasistoryappkriteria.ui.register

import android.content.Context
import android.content.SharedPreferences

class UserPreference(context: Context) {
    private val preferenceName = "Kotlinkatta"
    private val sharedPref: SharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)


    fun saveString(key_name: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(key_name, text)
        editor.apply()
    }

    fun getPreferenceString(key_name: String): String? {
        return sharedPref.getString(key_name, null)
    }

    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }
}