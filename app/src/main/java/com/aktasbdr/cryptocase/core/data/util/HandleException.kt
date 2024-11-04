package com.aktasbdr.cryptocase.core.data.util

import android.content.Context
import com.aktasbdr.cryptocase.R
import com.google.gson.Gson
import com.aktasbdr.cryptocase.core.data.model.ErrorResponse
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.HttpException as RetrofitHttpException

import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@Singleton
class HandleException @Inject constructor(
    private val gson: Gson,
    @ApplicationContext private val context: Context
) {
    operator fun invoke(exception: Throwable): Exceptions {
        println("Received Exception: ${exception::class.java}")

        val mappedException = when (exception) {
            is SocketTimeoutException -> Exceptions.TimeoutException()
            is UnknownHostException -> Exceptions.NetworkException()
            is IOException -> Exceptions.NetworkException()
            is JsonParseException,
            is JsonSyntaxException -> Exceptions.SerializationException()
            is RetrofitHttpException -> handleHttpException(exception)
            is Exceptions -> exception
            else -> Exceptions.CommonException()
        }

        println("Mapped to: ${mappedException::class.java}")
        return mappedException
    }

    private fun handleHttpException(exception: RetrofitHttpException): Exceptions {
        return try {
            when (exception.code()) {
                // 2xx Success
                in 200..299 -> {
                    val response = gson.fromJson(
                        exception.response()?.errorBody()?.charStream(),
                        ErrorResponse::class.java
                    )
                    Exceptions.HttpException(
                        errorMessage = response?.message ?: context.getString(R.string.error_unknown),
                        code = exception.code()
                    )
                }

                // 4xx Client Errors
                400 -> Exceptions.BadRequestException()
                401 -> Exceptions.UnauthorizedException()
                403 -> Exceptions.ForbiddenException()
                404 -> Exceptions.NotFoundException()
                408 -> Exceptions.TimeoutException()
                429 -> Exceptions.TooManyRequestsException()
                in 400..499 -> Exceptions.ClientErrorException()

                // 5xx Server Errors
                500 -> Exceptions.InternalServerException()
                502 -> Exceptions.BadGatewayException()
                503 -> Exceptions.ServiceUnavailableException()
                504 -> Exceptions.GatewayTimeoutException()
                in 500..599 -> Exceptions.ServerException()

                else -> Exceptions.HttpException(
                    errorMessage = context.getString(R.string.error_unknown),
                    code = exception.code()
                )
            }
        } catch (e: Exception) {
            Exceptions.ServerException()
        }
    }
}