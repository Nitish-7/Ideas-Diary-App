package com.app.diary.worker.sync

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.diary.db.NoteDao
import com.app.diary.models.NoteResponse
import com.app.diary.repository.OfflineNoteRepository
import com.app.diary.repository.OnlineNoteRepository
import com.app.diary.util.ApiResultHandler
import com.app.diary.worker.db.IdManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject

class SyncRemoteToLocal @Inject constructor(
    private val onlineNoteRepository: OnlineNoteRepository,
    private val noteDao: NoteDao,
    private val idManager: IdManager
) {
    val notesList: LiveData<ApiResultHandler<List<NoteResponse>>>
        get() = onlineNoteRepository.noteData

    fun sync() {
        notesList.value?.data?.forEach {
            if (idManager.getLocalIdFromRemoteId(it._id) == -1L) {
                GlobalScope.async {
                    val lId = noteDao.createNote(it)
                    idManager.createIdMap(lId, it._id)
                }
            }
        }
    }

    fun getNotes() {
        GlobalScope.async {
            onlineNoteRepository.getNotes()
        }
    }
}