package com.app.diary.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.diary.api.UserNoteApi
import com.app.diary.models.NoteRequest
import com.app.diary.models.NoteResponse
import com.app.diary.util.ApiResultHandler
import com.app.diary.util.BaseRepository
import com.app.diary.util.Constants
import javax.inject.Inject

class OnlineNoteRepository @Inject constructor(private val userNoteApi: UserNoteApi): BaseRepository() {
    private val _noteData=MutableLiveData<ApiResultHandler<List<NoteResponse>>>()
    val noteData get()=_noteData

    private val _noteChangeStatus=MutableLiveData<ApiResultHandler<NoteResponse>>()
    val noteChangeStatus get()=_noteChangeStatus

    suspend fun getNotes(){
        _noteData.postValue(ApiResultHandler.Loading())
        val response= safeCall { userNoteApi.getNotes() }
        _noteData.postValue(response)
    }

    suspend fun createNote(noteRequest: NoteRequest){
        _noteChangeStatus.postValue(ApiResultHandler.Loading())
        val response= safeCall { userNoteApi.createNote(noteRequest) }
        _noteChangeStatus.postValue(response)
    }
    suspend fun updateNote(noteId:String,noteRequest: NoteRequest){
        _noteChangeStatus.postValue(ApiResultHandler.Loading())
        val response= safeCall { userNoteApi.updateNote(noteId,noteRequest) }
        _noteChangeStatus.postValue(response)
    }
    suspend fun deleteNote(noteId:String){
        _noteChangeStatus.postValue(ApiResultHandler.Loading())
        val response= safeCall { userNoteApi.deleteNote(noteId) }
        _noteChangeStatus.postValue(response)
    }
}