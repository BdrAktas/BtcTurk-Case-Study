package com.aktasbdr.cryptocase.core.data.remote

import com.aktasbdr.cryptocase.core.data.util.Exceptions


sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val exception: Exceptions) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
}