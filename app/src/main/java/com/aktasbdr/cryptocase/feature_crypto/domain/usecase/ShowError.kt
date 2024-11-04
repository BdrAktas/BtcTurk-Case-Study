package com.aktasbdr.cryptocase.feature_crypto.domain.usecase

import android.content.Context
import com.aktasbdr.cryptocase.R
import com.aktasbdr.cryptocase.core.data.util.Exceptions
import com.aktasbdr.cryptocase.core.data.util.HandleException
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
        val handledException = if (exception is Exceptions) {
            exception
        } else {
            handleException(exception)
        }

        val message = when (handledException) {
            is Exceptions.NetworkException -> context.getString(R.string.error_no_internet)
            is Exceptions.TimeoutException -> context.getString(R.string.error_request_timeout)
            is Exceptions.TooManyRequestsException -> context.getString(R.string.error_too_many_requests)
            is Exceptions.SerializationException -> context.getString(R.string.error_serialization)
            is Exceptions.ServerException -> context.getString(R.string.error_unknown)
            is Exceptions.HttpException -> handledException.errorMessage
            is Exceptions.BadRequestException -> context.getString(R.string.error_bad_request)
            is Exceptions.UnauthorizedException -> context.getString(R.string.error_unauthorized)
            is Exceptions.ForbiddenException -> context.getString(R.string.error_forbidden)
            is Exceptions.NotFoundException -> context.getString(R.string.error_not_found)
            is Exceptions.ClientErrorException -> context.getString(R.string.error_client_error)
            is Exceptions.InternalServerException -> context.getString(R.string.error_internal_server_error)
            is Exceptions.BadGatewayException -> context.getString(R.string.error_bad_gateway)
            is Exceptions.ServiceUnavailableException -> context.getString(R.string.error_service_unavailable)
            is Exceptions.GatewayTimeoutException -> context.getString(R.string.error_gateway_timeout)
            else -> context.getString(R.string.error_unknown)
        }

        error.emit(message)
    }
}