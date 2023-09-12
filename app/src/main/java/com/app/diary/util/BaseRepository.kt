package com.app.diary.util

import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository {
    suspend fun <T> safeCall(
        res: suspend () -> Response<T>
    ): ApiResultHandler<T> {
        return try {
                val result=res()
                if (result.isSuccessful && result.body() != null) {
                    ApiResultHandler.Success(result.body())
                } else if (result.errorBody() != null) {
                    val jsonObject = JSONObject(result.errorBody()!!.charStream().readText())
                    ApiResultHandler.Failure(jsonObject.getString("message"))
                } else {
                    ApiResultHandler.Failure("Something went wrong!")
                }
            } catch (e: IOException) {
                ApiResultHandler.Failure("Something went wrong! Try reconnecting to internet")
            } catch (e: Exception) {
                ApiResultHandler.Failure("Something went wrong!")
            } as ApiResultHandler<T>
    }
}