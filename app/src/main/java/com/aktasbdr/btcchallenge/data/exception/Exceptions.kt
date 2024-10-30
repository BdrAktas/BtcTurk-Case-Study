package com.aktasbdr.btcchallenge.data.exception

sealed class Exceptions : Exception() {

    object CommonException : Exceptions()

    data class HttpException(
        val errorMessage: String
    ) : Exceptions()
}
