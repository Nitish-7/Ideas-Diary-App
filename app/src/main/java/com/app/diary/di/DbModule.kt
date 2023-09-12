package com.app.diary.di

import android.content.Context
import androidx.room.Room
import com.app.diary.db.NoteDao
import com.app.diary.db.NotesDb
import com.app.diary.util.Constants
import com.app.diary.worker.db.NoteSyncDao
import com.app.diary.worker.model.NoteSync
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DbModule {
    @Provides
    @Singleton
    fun provideNotesDb(@ApplicationContext context: Context):NotesDb{
        return Room.databaseBuilder(context,NotesDb::class.java,Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideNotesDao(notesDb: NotesDb):NoteDao{
        return notesDb.getNotesDao()
    }

    @Provides
    @Singleton
    fun provideNotesSyncDao(notesDb: NotesDb): NoteSyncDao {
        return notesDb.getNoteSyncDao()
    }
}