package com.app.diary.models

data class UserResponse(
    val token: String,
    val user: User
)