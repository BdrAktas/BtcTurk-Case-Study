package com.aktasbdr.cryptocase.core.data.remote

import com.aktasbdr.cryptocase.data.exception.HandleException
import javax.inject.Inject

class SafeApiCall @Inject constructor(
    private val handleException: HandleException
) {
    suspend operator fun <T> invoke(
        apiCall: suspend () -> T
    ): NetworkResult<T> {
        return try {
            NetworkResult.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            NetworkResult.Error(handleException(throwable))
        }
    }
}


