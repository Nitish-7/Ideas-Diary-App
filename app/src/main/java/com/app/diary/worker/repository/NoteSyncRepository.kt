package com.app.diary.worker.repository

import com.app.diary.worker.db.NoteSyncDao
import com.app.diary.worker.model.NoteSync
import javax.inject.Inject

class NoteSyncRepository @Inject constructor(private val noteSyncDao: NoteSyncDao) {
    val notesToCreate=noteSyncDao.getNotesToCreate()
    val notesToUpdate=noteSyncDao.getNotesToUpdate()
    val notesToDelete=noteSyncDao.getNotesToDelete()

    fun createSyncNote(noteSync: NoteSync) {
        noteSyncDao.createSyncNote(noteSync)
    }
    fun deleteSyncNote(noteSync: NoteSync) {
        noteSyncDao.deleteSyncNote(noteSync)
    }
}