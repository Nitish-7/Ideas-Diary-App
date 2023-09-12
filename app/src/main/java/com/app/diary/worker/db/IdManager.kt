package com.app.diary.worker.db

import android.content.Context
import com.app.diary.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class IdManager @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreference =
        context.getSharedPreferences(Constants.ID_TO_ID_MAP_FILE, Context.MODE_PRIVATE)

    fun getRemoteIdFromLocalId(lId: Long): String {
        return sharedPreference.getString(lId.toString(), null).toString()
    }

    fun setRemoteIdFromLocalId(lId: Long, rId: String) {
        val editor = sharedPreference.edit()
        editor.putString(lId.toString(), rId)
        editor.apply()
    }

    fun getLocalIdFromRemoteId(rId: String): Long {
        return sharedPreference.getLong(rId, -1)
    }

    fun setLocalIdFromRemoteId(lId: Long, rId: String) {
        val editor = sharedPreference.edit()
        editor.putLong(rId, lId)
        editor.apply()
    }
    fun createIdMap(lId: Long, rId: String){
        setLocalIdFromRemoteId(lId,rId)
        setRemoteIdFromLocalId(lId,rId)
    }
}