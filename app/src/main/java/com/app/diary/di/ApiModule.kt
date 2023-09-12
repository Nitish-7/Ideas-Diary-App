package com.app.diary.di

import com.app.diary.api.HeadersInterceptor
import com.app.diary.api.UserApi
import com.app.diary.api.UserNoteApi
import com.app.diary.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideUserApi(retrofitBuilder: Retrofit.Builder):UserApi{
        return retrofitBuilder.build().create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUserNoteApi(retrofitBuilder: Retrofit.Builder, httpClient:OkHttpClient):UserNoteApi{
        return retrofitBuilder.client(httpClient).build().create(UserNoteApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilderInstance():Retrofit.Builder{
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideHttpClient(headersInterceptor: HeadersInterceptor):OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(headersInterceptor).build()
    }

}