package com.example.submissionaplikasistoryappkriteria.ui.register

import android.content.Context
import android.content.SharedPreferences

class UserPreference(context: Context) {
    private val preference_Name = "Kotlinkatta"
    val shared_Pref: SharedPreferences = context.getSharedPreferences(preference_Name, Context.MODE_PRIVATE)


    fun save_String(key_name: String, text: String) {
        val editor: SharedPreferences.Editor = shared_Pref.edit()
        editor.putString(key_name, text)
        editor.commit()
    }

    fun getPreferenceString(key_name: String): String? {
        return shared_Pref.getString(key_name, null)
    }

    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = shared_Pref.edit()
        editor.clear()
        editor.commit()
    }
}