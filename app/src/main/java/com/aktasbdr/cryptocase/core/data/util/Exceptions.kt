package com.aktasbdr.cryptocase.core.data.util

import com.aktasbdr.cryptocase.R
import com.aktasbdr.cryptocase.core.domain.util.NetworkError

sealed class Exceptions : Exception() {
    data class CommonException(
        override val message: String = R.string.error_unknown.toString()
    ) : Exceptions()

    data class HttpException(
        val errorMessage: String,
        val code: Int,
        override val message: String = errorMessage
    ) : Exceptions()

    data class NetworkException(
        val error: NetworkError = NetworkError.NO_INTERNET
    ) : Exceptions()

    data class TimeoutException(
        val error: NetworkError = NetworkError.REQUEST_TIMEOUT
    ) : Exceptions()

    data class SerializationException(
        val error: NetworkError = NetworkError.SERIALIZATION
    ) : Exceptions()

    data class ServerException(
        val error: NetworkError = NetworkError.SERVER_ERROR
    ) : Exceptions()

    data class TooManyRequestsException(
        val error: NetworkError = NetworkError.TOO_MANY_REQUESTS
    ) : Exceptions()

    data class BadRequestException(
        val error: NetworkError = NetworkError.BAD_REQUEST
    ) : Exceptions()

    data class UnauthorizedException(
        val error: NetworkError = NetworkError.UNAUTHORIZED
    ) : Exceptions()

    data class ForbiddenException(
        val error: NetworkError = NetworkError.FORBIDDEN
    ) : Exceptions()

    data class NotFoundException(
        val error: NetworkError = NetworkError.NOT_FOUND
    ) : Exceptions()

    data class ClientErrorException(
        val error: NetworkError = NetworkError.CLIENT_ERROR
    ) : Exceptions()

    data class InternalServerException(
        val error: NetworkError = NetworkError.INTERNAL_SERVER_ERROR
    ) : Exceptions()

    data class BadGatewayException(
        val error: NetworkError = NetworkError.BAD_GATEWAY
    ) : Exceptions()

    data class ServiceUnavailableException(
        val error: NetworkError = NetworkError.SERVICE_UNAVAILABLE
    ) : Exceptions()

    data class GatewayTimeoutException(
        val error: NetworkError = NetworkError.GATEWAY_TIMEOUT
    ) : Exceptions()
}
