package com.app.diary.api

import com.app.diary.util.Constants
import com.app.diary.util.UserTokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeadersInterceptor @Inject constructor(private val userTokenManager: UserTokenManager ) :Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request().newBuilder()

        val token = userTokenManager.getToken()
        oldRequest.addHeader("Authorization"," Bearer $token")

        return chain.proceed(oldRequest.build())
    }
}