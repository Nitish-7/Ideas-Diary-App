package com.app.diary.util

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserTokenManager @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreference =
        context.getSharedPreferences(Constants.USER_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor=sharedPreference.edit()
        editor.putString(Constants.TOKEN,token)
        editor.apply()
    }
    fun getToken():String?{
        return sharedPreference.getString(Constants.TOKEN,null)
    }
}