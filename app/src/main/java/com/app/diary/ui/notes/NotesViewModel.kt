package com.app.diary.ui.notes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.diary.models.NoteRequest
import com.app.diary.models.NoteResponse
import com.app.diary.repository.OfflineNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val offlineNoteRepository: OfflineNoteRepository):ViewModel() {

    val noteData get()=offlineNoteRepository.noteData
    val noteChangeStatus get()=offlineNoteRepository.noteChangeStatus

    fun createNote(noteRequest: NoteRequest){
        CoroutineScope(Dispatchers.IO).launch{
            offlineNoteRepository.createNote(noteRequest)
        }

    }
    fun updateNote(note: NoteResponse){
        CoroutineScope(Dispatchers.IO).launch{
            offlineNoteRepository.updateNote(note)
        }
    }
    fun deleteNote(note:NoteResponse){
        CoroutineScope(Dispatchers.IO).launch{
            offlineNoteRepository.deleteNote(note)
        }
    }
}