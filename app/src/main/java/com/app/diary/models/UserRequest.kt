package com.app.diary.models

data class UserRequest(
    var email: String,
    var password: String,
    var username: String
)