package com.aktasbdr.cryptocase.core.data.util

import com.aktasbdr.cryptocase.core.domain.util.NetworkError

sealed class Exceptions : Exception() {
    data class CommonException(
        override val message: String = "Bilinmeyen bir hata oluştu. Lütfen tekrar deneyin."
    ) : Exceptions()

    data class HttpException(
        val errorMessage: String,
        val code: Int,
        override val message: String = errorMessage
    ) : Exceptions()

    data class NetworkException(
        override val message: String = "İnternet bağlantısı yok. Lütfen bağlantınızı kontrol edin.",
        val error: NetworkError = NetworkError.NO_INTERNET
    ) : Exceptions()

    data class TimeoutException(
        override val message: String = "İstek zaman aşımına uğradı. Lütfen daha sonra tekrar deneyin.",
        val error: NetworkError = NetworkError.REQUEST_TIMEOUT
    ) : Exceptions()

    data class SerializationException(
        override val message: String = "Veri işleme hatası oluştu. Lütfen tekrar deneyin.",
        val error: NetworkError = NetworkError.SERIALIZATION
    ) : Exceptions()

    data class ServerException(
        override val message: String = "Sunucu hatası oluştu. Lütfen daha sonra tekrar deneyin.",
        val error: NetworkError = NetworkError.SERVER_ERROR
    ) : Exceptions()

    data class TooManyRequestsException(
        override val message: String = "Çok fazla istek gönderildi. Lütfen daha sonra tekrar deneyin.",
        val error: NetworkError = NetworkError.TOO_MANY_REQUESTS
    ) : Exceptions()
}
