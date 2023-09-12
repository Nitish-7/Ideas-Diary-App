package com.app.diary.ui.registration

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.diary.models.UserRequest
import com.app.diary.repository.UserAuthRepository
import com.app.diary.util.ApiResultHandler
import com.app.diary.util.Helper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val userAuthRepository: UserAuthRepository) :
    ViewModel() {
    val userResponseLiveData get() = userAuthRepository.userResponseLiveData
    val userRequest = MutableLiveData(UserRequest("","",""))
    val userRequestError = MutableLiveData<String>()

    fun registerUser() {
        if (validate(true)) {
            viewModelScope.launch {
                userRequest.value?.let { userAuthRepository.registerUser(it) }
            }
        }
    }

    fun loginUser() {
        if (validate(false)) {
            viewModelScope.launch {
                userRequest.value?.let { userAuthRepository.loginUser(it) }
            }
        }
    }

    fun validate(isSignUp: Boolean): Boolean {
        userRequest.value?.run {
            if (TextUtils.isEmpty(email) || (isSignUp && TextUtils.isEmpty(username))
                || TextUtils.isEmpty(password)
            ) {
                userRequestError.postValue("Please provide the credentials")
                return false
            } else if (!Helper.isValidEmail(email)) {
                userRequestError.postValue("Email is invalid")
                return false
            } else if (!TextUtils.isEmpty(password) && password.length <= 5) {
                userRequestError.postValue("Password length should be greater than 5")
                return false
            }
        }
        return true
    }

}