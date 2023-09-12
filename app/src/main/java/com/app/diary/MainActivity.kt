package com.app.diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.app.diary.api.UserApi
import com.app.diary.api.UserNoteApi
import com.app.diary.models.NoteRequest
import com.app.diary.models.UserRequest
import com.app.diary.repository.UserAuthRepository
import com.app.diary.ui.notes.NotesViewModel
import com.app.diary.ui.registration.RegistrationViewModel
import com.app.diary.util.ApiResultHandler
import com.app.diary.util.UserTokenManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val notesViewModel by viewModels<NotesViewModel>()
    private val registrationViewModel by viewModels<RegistrationViewModel>()
    @Inject
    lateinit var userTokenManager: UserTokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userRequest = UserRequest("abcd", "abc12@gmail.com", "1234567")
        //val noteRequest = NoteRequest("note from app", "dfkjfij dfhjisjda fiosduifj fodsufo dof ")
        registrationViewModel.loginUser(userRequest)
        registrationViewModel.userResponseLiveData.observe(this, Observer {
            when(it){
                is ApiResultHandler.Failure -> Log.d("# user", it.message!!)
                is ApiResultHandler.Loading -> Log.d("# user", "is loading.........")
                is ApiResultHandler.Success -> userTokenManager.saveToken(it.data!!.token)
            }
        })
//        notesViewModel.getNotes()
//        notesViewModel.noteData.observe(this, Observer {
//            when (it) {
//                is ApiResultHandler.Failure -> Log.d("# notes", it.message!!)
//                is ApiResultHandler.Loading -> Log.d("# notes", "is loading.........")
//                is ApiResultHandler.Success -> Log.d("# notes", it.data.toString())
//            }
//        })


    }
}