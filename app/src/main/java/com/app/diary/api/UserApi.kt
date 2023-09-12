package com.app.diary.api

import com.app.diary.models.UserRequest
import com.app.diary.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/users/signup")
    suspend fun registerUser(@Body userRequest: UserRequest):Response<UserResponse>

    @POST("/users/signin")
    suspend fun loginUser(@Body userRequest: UserRequest):Response<UserResponse>
}