package com.aktasbdr.cryptocase.core.presentation.util

import android.content.Context
import com.aktasbdr.cryptocase.R
import com.aktasbdr.cryptocase.core.domain.util.NetworkError


fun NetworkError.toString(context: Context): String {
    val resId = when (this) {
        NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        NetworkError.NO_INTERNET -> R.string.error_no_internet
        NetworkError.SERVER_ERROR -> R.string.error_unknown
        NetworkError.SERIALIZATION -> R.string.error_serialization
        NetworkError.UNKNOWN -> R.string.error_unknown

        NetworkError.BAD_REQUEST -> R.string.error_bad_request
        NetworkError.UNAUTHORIZED -> R.string.error_unauthorized
        NetworkError.FORBIDDEN -> R.string.error_forbidden
        NetworkError.NOT_FOUND -> R.string.error_not_found
        NetworkError.CLIENT_ERROR -> R.string.error_client_error
        NetworkError.INTERNAL_SERVER_ERROR -> R.string.error_internal_server_error
        NetworkError.BAD_GATEWAY -> R.string.error_bad_gateway
        NetworkError.SERVICE_UNAVAILABLE -> R.string.error_service_unavailable
        NetworkError.GATEWAY_TIMEOUT -> R.string.error_gateway_timeout
    }
    return context.getString(resId)
}