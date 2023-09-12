package com.app.diary.repository

import android.util.JsonReader
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.diary.api.UserApi
import com.app.diary.models.UserRequest
import com.app.diary.models.UserResponse
import com.app.diary.util.ApiResultHandler
import com.app.diary.util.BaseRepository
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserAuthRepository @Inject constructor(private val userApi: UserApi) : BaseRepository() {
    private val _userResponseLiveData = MutableLiveData<ApiResultHandler<UserResponse>>()
    val userResponseLiveData: LiveData<ApiResultHandler<UserResponse>>
        get() = _userResponseLiveData

    suspend fun registerUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(ApiResultHandler.Loading())
        val response = safeCall { userApi.registerUser(userRequest) }
        _userResponseLiveData.postValue(response)
    }

    suspend fun loginUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(ApiResultHandler.Loading())
        val response = safeCall { userApi.loginUser(userRequest) }
        _userResponseLiveData.postValue(response)
    }

}