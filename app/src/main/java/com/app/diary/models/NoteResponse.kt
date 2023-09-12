package com.app.diary.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.diary.util.Constants

@Entity(tableName = Constants.NOTES_TABLE_NAME)
data class NoteResponse(
    @PrimaryKey(autoGenerate = true)
    val l_id: Long=0,
    val _id: String="",
    val __v: Int=0,
    val createdAt: String="",
    val description: String="",
    val title: String="",
    val updatedAt: String="",
    val userId: String=""
)