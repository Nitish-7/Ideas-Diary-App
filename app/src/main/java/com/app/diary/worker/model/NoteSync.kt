package com.app.diary.worker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.diary.util.Constants

@Entity(tableName = Constants.SYNC_NOTES_TABLE_NAME)
data class NoteSync(
    @PrimaryKey(autoGenerate = true)
    val s_id: Long=0,
    val l_id:Long=0,
    val title: String,
    val description: String,
    val operationType: String // Create, Update, Delete
)