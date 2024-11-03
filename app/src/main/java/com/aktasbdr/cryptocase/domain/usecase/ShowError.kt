package com.aktasbdr.cryptocase.domain.usecase

import android.content.Context
import com.aktasbdr.cryptocase.R
import com.aktasbdr.cryptocase.core.data.util.Exceptions
import com.aktasbdr.cryptocase.data.exception.HandleException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowError @Inject constructor(
    private val handleException: HandleException,
    @ApplicationContext private val context: Context
) {
    private val error = MutableSharedFlow<String>()

    operator fun invoke(): SharedFlow<String> = error.asSharedFlow()

    suspend operator fun invoke(exception: Throwable) {
        println("ShowError received: ${exception::class.java}")

        val handledException = if (exception is Exceptions) {
            exception
        } else {
            handleException(exception)
        }

        println("ShowError final exception: ${handledException::class.java}")

        val message = when (handledException) {
            is Exceptions.NetworkException -> context.getString(R.string.error_no_internet)
            is Exceptions.TimeoutException -> context.getString(R.string.error_request_timeout)
            is Exceptions.TooManyRequestsException -> context.getString(R.string.error_too_many_requests)
            is Exceptions.SerializationException -> context.getString(R.string.error_serialization)
            is Exceptions.ServerException -> handledException.message
            is Exceptions.HttpException -> handledException.errorMessage
            is Exceptions.CommonException -> context.getString(R.string.error_unknown)
        }

        error.emit(message)
    }
}
