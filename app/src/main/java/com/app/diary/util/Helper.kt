package com.app.diary.util

import android.content.Context
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.app.diary.models.NoteRequest
import com.app.diary.models.NoteResponse
import com.app.diary.worker.model.NoteSync
import java.util.regex.Matcher
import java.util.regex.Pattern

class Helper {
    companion object {
        fun isValidEmail(email: String): Boolean {
            return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun hideKeyboard(view: View) {
            try {
                val imm =
                    view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            } catch (e: Exception) {

            }
        }

        fun getNoteSync(
            noteResponse: NoteResponse? = null,
            noteRequest: NoteRequest? = null,
            l_id: Long? = null,
            operationType: String
        ): NoteSync {
            return noteRequest?.let {
                NoteSync(
                    l_id = l_id ?: 0,
                    title = it.title,
                    description = it.description,
                    operationType = operationType
                )
            } ?: noteResponse?.run {
                NoteSync(
                    l_id = this.l_id,
                    title = title,
                    description = description,
                    operationType = operationType
                )
            }!!
        }
    }
}