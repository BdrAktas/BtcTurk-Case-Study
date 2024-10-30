package com.aktasbdr.cryptocase.data.exception

import com.google.gson.Gson
import com.aktasbdr.cryptocase.data.exception.Exceptions.CommonException
import com.aktasbdr.cryptocase.data.exception.Exceptions.HttpException
import com.aktasbdr.cryptocase.data.model.ErrorResponse
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.HttpException as RetrofitHttpException

@Singleton
class HandleException @Inject constructor(
    private val gson: Gson
) {

    operator fun invoke(exception: Throwable): Exceptions {
        return when (exception) {
            is RetrofitHttpException -> runCatching {
                val response = gson.fromJson(
                    exception.response()!!.errorBody()!!.charStream(),
                    ErrorResponse::class.java
                )
                HttpException(response.message.orEmpty())
            }.getOrDefault(CommonException)
            else -> CommonException
        }
    }
}
