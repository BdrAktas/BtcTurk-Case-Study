package com.aktasbdr.cryptocase.ui

sealed class MainUiEvent {
    data class ShowErrorMessage(
        val message: String
    ) : MainUiEvent()
}
