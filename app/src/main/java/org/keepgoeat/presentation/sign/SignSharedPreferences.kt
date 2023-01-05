package org.keepgoeat.presentation.sign

import android.content.Context
import android.content.SharedPreferences

object SignSharedPreferences {
    fun setUserToken(context: Context, token: String) {
        val prefs: SharedPreferences = context.getSharedPreferences("account", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString("access_token", token)
        editor.commit()
    }

    fun getUserToken(context: Context): String {
        val prefs: SharedPreferences = context.getSharedPreferences("account", Context.MODE_PRIVATE)
        return prefs.getString("access_token", "").toString()
    }
}
