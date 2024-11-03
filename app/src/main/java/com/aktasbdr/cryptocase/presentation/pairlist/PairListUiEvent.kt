package com.aktasbdr.cryptocase.presentation.pairlist

sealed class PairListUiEvent {
    data class NavigateToPairChart(
        val pairNormalized: String
    ) : PairListUiEvent()
    data class ShowError(val message: String) : PairListUiEvent()

}