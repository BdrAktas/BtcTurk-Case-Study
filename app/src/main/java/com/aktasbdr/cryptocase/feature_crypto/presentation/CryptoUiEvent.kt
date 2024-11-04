package com.aktasbdr.cryptocase.feature_crypto.presentation

sealed class CryptoUiEvent {
    data class ShowErrorMessage(
        val message: String
    ) : CryptoUiEvent()
}
