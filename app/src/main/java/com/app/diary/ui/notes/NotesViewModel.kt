package com.app.diary.ui.notes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.diary.models.NoteRequest
import com.app.diary.models.NoteResponse
import com.app.diary.repository.UserNoteRepository
import com.app.diary.util.ApiResultHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val noteRepository: UserNoteRepository):ViewModel() {

    val noteData get()=noteRepository.noteData
    val noteChangeStatus get()=noteRepository.noteChangeStatus

    fun getNotes(){
       viewModelScope.launch{
           noteRepository.getNotes()
       }
    }

    suspend fun createNote(noteRequest: NoteRequest){
        viewModelScope.launch{
            noteRepository.createNote(noteRequest)
        }
    }
    suspend fun updateNote(noteId:String,noteRequest: NoteRequest){
        viewModelScope.launch{
            noteRepository.updateNote(noteId, noteRequest)
        }
    }
    suspend fun deleteNote(noteId:String){
        viewModelScope.launch{
            noteRepository.deleteNote(noteId)
        }
    }
}