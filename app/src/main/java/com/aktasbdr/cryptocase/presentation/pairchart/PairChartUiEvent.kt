package com.aktasbdr.cryptocase.presentation.pairchart

sealed class PairChartUiEvent {
    data class ShowError(val message: String) : PairChartUiEvent()
}
