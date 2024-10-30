package com.aktasbdr.btcchallenge.ui

sealed class MainUiEvent {
    data class ShowErrorMessage(
        val message: String
    ) : MainUiEvent()
}
