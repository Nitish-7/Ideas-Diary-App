package com.app.diary.worker.sync

import com.app.diary.models.NoteRequest
import com.app.diary.repository.OnlineNoteRepository
import com.app.diary.util.ApiResultHandler
import com.app.diary.worker.db.IdManager
import com.app.diary.worker.repository.NoteSyncRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SyncLocalToRemote @Inject constructor(
    private val noteSyncRepository: NoteSyncRepository,
    private val onlineNoteRepository: OnlineNoteRepository,
    private val idManager: IdManager
) {
    val notesToCreate = noteSyncRepository.notesToCreate
    val notesToUpdate = noteSyncRepository.notesToUpdate
    val notesToDelete = noteSyncRepository.notesToDelete
    val noteChangeStatus=onlineNoteRepository.noteChangeStatus

    suspend fun sync() {
        synchronized(this) {
            notesToCreate.value?.let { noteSync ->
                for (note in noteSync) {
                    GlobalScope.async {
                        onlineNoteRepository.createNote(NoteRequest(note.title, note.description))
                    }
                    onlineNoteRepository.noteChangeStatus.value.let {
                        if (it is ApiResultHandler.Failure) {
                            return
                        } else if (it is ApiResultHandler.Success) {
                            val rId = it.data!!._id
                            idManager.createIdMap(note.l_id, rId)
                            noteSyncRepository.deleteSyncNote(note)
                        }
                    }
                }
            }
            notesToUpdate.value?.let { noteSync ->
                for (note in noteSync) {
                    GlobalScope.async {
                        onlineNoteRepository.updateNote(
                            idManager.getRemoteIdFromLocalId(note.l_id),
                            NoteRequest(note.title, note.description)
                        )
                    }
                    onlineNoteRepository.noteChangeStatus.value.let {
                        if (it is ApiResultHandler.Failure) {
                            return
                        } else if (it is ApiResultHandler.Success) {
                            noteSyncRepository.deleteSyncNote(note)
                        }
                    }
                }
            }
            notesToDelete.value?.let { noteSync ->
                for (note in noteSync) {
                    GlobalScope.async {
                        onlineNoteRepository.deleteNote(idManager.getRemoteIdFromLocalId(note.l_id))
                    }
                    onlineNoteRepository.noteChangeStatus.value.let {
                        if (it is ApiResultHandler.Failure) {
                            return
                        } else if (it is ApiResultHandler.Success){
                            noteSyncRepository.deleteSyncNote(note)
                        }
                    }
                }
            }
        }
    }
    interface Litsener{
        fun deleteSyncNoteOfType()
    }

}




