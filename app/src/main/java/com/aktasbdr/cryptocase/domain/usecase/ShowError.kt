package com.aktasbdr.cryptocase.domain.usecase

import com.aktasbdr.cryptocase.data.exception.Exceptions
import com.aktasbdr.cryptocase.data.exception.HandleException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowError @Inject constructor(
    private val handleException: HandleException
) {
    private val error = MutableSharedFlow<String>()

    operator fun invoke(): SharedFlow<String> = error.asSharedFlow()

    suspend operator fun invoke(exception: Throwable) {

        val handledException = if (exception is Exceptions) {
            exception
        } else {
            handleException(exception)
        }

        val message = when (handledException) {
            is Exceptions.NetworkException -> handledException.message
            is Exceptions.TimeoutException -> handledException.message
            is Exceptions.HttpException -> handledException.errorMessage
            is Exceptions.CommonException -> handledException.message
        }

        error.emit(message)
    }
}
