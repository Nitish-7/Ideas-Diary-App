package com.app.diary.worker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.diary.models.NoteResponse
import com.app.diary.util.Constants
import com.app.diary.worker.model.NoteSync

@Dao
interface NoteSyncDao {
    @Insert
    fun createSyncNote( noteSync: NoteSync)

    @Delete
    fun deleteSyncNote(noteSync: NoteSync)

    @Query("SELECT * FROM ${Constants.SYNC_NOTES_TABLE_NAME} WHERE ${Constants.DB_OPERATION.TYPE} = :${Constants.DB_OPERATION.TYPE}")
    fun getNotesToCreate(operationType:String=Constants.DB_OPERATION.CREATE): LiveData<List<NoteSync>>

    @Query("SELECT * FROM ${Constants.SYNC_NOTES_TABLE_NAME} WHERE ${Constants.DB_OPERATION.TYPE} = :${Constants.DB_OPERATION.TYPE}")
    fun getNotesToUpdate(operationType:String=Constants.DB_OPERATION.UPDATE): LiveData<List<NoteSync>>

    @Query("SELECT * FROM ${Constants.SYNC_NOTES_TABLE_NAME} WHERE ${Constants.DB_OPERATION.TYPE} = :${Constants.DB_OPERATION.TYPE}")
    fun getNotesToDelete(operationType:String=Constants.DB_OPERATION.DELETE): LiveData<List<NoteSync>>
}