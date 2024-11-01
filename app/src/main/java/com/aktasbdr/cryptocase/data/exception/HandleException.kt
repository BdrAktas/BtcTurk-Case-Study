package com.aktasbdr.cryptocase.data.exception

import com.google.gson.Gson
import com.aktasbdr.cryptocase.data.model.ErrorResponse
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.HttpException as RetrofitHttpException

import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@Singleton
class HandleException @Inject constructor(
    private val gson: Gson
) {
    operator fun invoke(exception: Throwable): Exceptions {

        val mappedException = when (exception) {
            is SocketTimeoutException -> Exceptions.TimeoutException
            is UnknownHostException -> Exceptions.NetworkException
            is IOException -> Exceptions.NetworkException
            is RetrofitHttpException -> handleHttpException(exception)
            is JsonParseException, is JsonSyntaxException -> Exceptions.CommonException
            is Exceptions -> exception // Eğer zaten bizim Exception türlerimizden biriyse direkt geçir
            else -> Exceptions.CommonException
        }

        return mappedException
    }

    private fun handleHttpException(exception: RetrofitHttpException): Exceptions {
        return try {
            val response = gson.fromJson(
                exception.response()?.errorBody()?.charStream(),
                ErrorResponse::class.java
            )
            Exceptions.HttpException(
                response?.message ?: "Sunucu hatası oluştu. Lütfen tekrar deneyin."
            )
        } catch (e: Exception) {
            Exceptions.HttpException("Sunucu hatası oluştu. Lütfen tekrar deneyin.")
        }
    }
}