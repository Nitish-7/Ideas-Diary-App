package com.app.diary.repository

import androidx.lifecycle.MutableLiveData
import com.app.diary.db.NoteDao
import com.app.diary.db.NotesDb
import com.app.diary.models.NoteRequest
import com.app.diary.models.NoteResponse
import com.app.diary.util.ApiResultHandler
import com.app.diary.util.Constants
import com.app.diary.util.Helper
import com.app.diary.worker.db.NoteSyncDao
import com.app.diary.worker.model.NoteSync
import com.app.diary.worker.repository.NoteSyncRepository
import javax.inject.Inject

class OfflineNoteRepository @Inject constructor(private val notesDao: NoteDao,private val noteSyncRepository: NoteSyncRepository) {
    val noteData =notesDao.getNotes()
    private val _noteChangeStatus= MutableLiveData<String>()
    val noteChangeStatus get()=_noteChangeStatus

    suspend fun createNote(noteRequest: NoteRequest){
        val note=noteRequest.run{
            NoteResponse(title = title, description = description)
        }
        val lId=notesDao.createNote(note)
        val noteSync=Helper.getNoteSync(l_id = lId, noteRequest = noteRequest, operationType = Constants.DB_OPERATION.CREATE)
        noteSyncRepository.createSyncNote(noteSync)
    }
    suspend fun updateNote(note: NoteResponse){
        notesDao.updateNote(note)
        val noteSync=Helper.getNoteSync(noteResponse = note, operationType = Constants.DB_OPERATION.UPDATE)
        noteSyncRepository.createSyncNote(noteSync)
    }
    suspend fun deleteNote(note:NoteResponse){
        notesDao.deleteNote(note)
        val noteSync=Helper.getNoteSync(noteResponse = note, operationType = Constants.DB_OPERATION.DELETE)
        noteSyncRepository.createSyncNote(noteSync)
    }

}