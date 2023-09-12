package com.app.diary.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.diary.models.NoteResponse
import com.app.diary.worker.db.NoteSyncDao
import com.app.diary.worker.model.NoteSync

@Database(entities = [NoteResponse::class,NoteSync::class], version = 2)
abstract class NotesDb:RoomDatabase(){
    abstract fun getNotesDao():NoteDao
    abstract fun getNoteSyncDao():NoteSyncDao
}