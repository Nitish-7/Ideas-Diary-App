package com.app.diary.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.diary.models.UserRequest
import com.app.diary.repository.UserAuthRepository
import com.app.diary.util.ApiResultHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val userAuthRepository: UserAuthRepository) :ViewModel(){
    val userResponseLiveData get() = userAuthRepository.userResponseLiveData

     fun registerUser(userRequest: UserRequest) {
       viewModelScope.launch {
           userAuthRepository.registerUser(userRequest)
       }
    }

    fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userAuthRepository.loginUser(userRequest)
        }
    }

}