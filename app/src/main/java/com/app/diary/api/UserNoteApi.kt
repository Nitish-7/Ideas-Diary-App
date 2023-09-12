package com.app.diary.api

import androidx.room.Delete
import com.app.diary.models.NoteRequest
import com.app.diary.models.NoteResponse
import com.app.diary.util.Constants
import retrofit2.Response
import retrofit2.http.*

interface UserNoteApi {
    @POST("/note")
    suspend fun createNote(@Body noteRequest: NoteRequest): Response<NoteResponse>

    @GET("/note")
    suspend fun getNotes(): Response<List<NoteResponse>>

    @PUT("/note/{${Constants.NOTE_ID}}")
    suspend fun updateNote(@Path(Constants.NOTE_ID,) noteId: String,
                           @Body noteRequest: NoteRequest): Response<NoteResponse>

    @DELETE("/note/{${Constants.NOTE_ID}}")
    suspend fun deleteNote(@Path(Constants.NOTE_ID) noteId: String): Response<NoteResponse>
}