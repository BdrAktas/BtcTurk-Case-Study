package com.aktasbdr.cryptocase.presentation

sealed class MainUiEvent {
    data class ShowErrorMessage(
        val message: String
    ) : MainUiEvent()
}
