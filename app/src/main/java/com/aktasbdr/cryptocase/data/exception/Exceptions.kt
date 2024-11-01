package com.aktasbdr.cryptocase.data.exception

sealed class Exceptions : Exception() {
    object CommonException : Exceptions() {
        private fun readResolve(): Any = CommonException
        override val message: String
            get() = "Bilinmeyen bir hata oluştu. Lütfen tekrar deneyin."
    }

    data class HttpException(
        val errorMessage: String,
        override val message: String = errorMessage
    ) : Exceptions()

    object NetworkException : Exceptions() {
        private fun readResolve(): Any = NetworkException
        override val message: String
            get() = "İnternet bağlantısı yok. Lütfen bağlantınızı kontrol edin."
    }

    object TimeoutException : Exceptions() {
        private fun readResolve(): Any = TimeoutException
        override val message: String
            get() = "İstek zaman aşımına uğradı. Lütfen daha sonra tekrar deneyin."
    }
}
