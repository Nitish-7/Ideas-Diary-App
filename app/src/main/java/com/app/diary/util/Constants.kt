package com.app.diary.util


object Constants {
    const val TOKEN="token"
    const val BASE_URL="https://noteapi-use.onrender.com/"
    const val NOTE_ID="noteId"
    const val USER_TOKEN_FILE="userTokenFile"
    const val ID_TO_ID_MAP_FILE= "idToIdMapDB"
    const val BUNDLE_NOTES_TO_EDIT="notes"
    const val DATABASE_NAME="NotesDb"
    const val NOTES_TABLE_NAME="notes"
    const val SYNC_NOTES_TABLE_NAME="sync_notes"

    object DB_OPERATION{
        const val TYPE="operationType"
        const val CREATE="created"
        const val UPDATE="updated"
        const val DELETE="deleted"
    }
}