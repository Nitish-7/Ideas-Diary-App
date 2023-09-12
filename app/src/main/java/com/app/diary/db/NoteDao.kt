package com.app.diary.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Query
import com.app.diary.models.NoteRequest
import com.app.diary.models.NoteResponse
import com.app.diary.util.Constants
import retrofit2.Response
import retrofit2.http.*

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createNote( noteRequest: NoteResponse):Long

    @Update
    fun updateNote(noteRequest: NoteResponse)

    @Delete
    fun deleteNote(noteRequest: NoteResponse)

    @Query("SELECT * FROM ${Constants.NOTES_TABLE_NAME}")
    fun getNotes(): LiveData<List<NoteResponse>>
}