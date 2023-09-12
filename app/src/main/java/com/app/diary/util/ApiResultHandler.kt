package com.app.diary.util

sealed class ApiResultHandler<T>(val data:T?=null, val message:String?=null) {
    class Success<T>(data: T):ApiResultHandler<T>(data = data)
    class Failure<T>(message: String):ApiResultHandler<T>(message=message)
    class Loading<T>():ApiResultHandler<T>()
}