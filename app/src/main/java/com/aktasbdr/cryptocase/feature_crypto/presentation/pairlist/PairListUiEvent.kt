package com.aktasbdr.cryptocase.feature_crypto.presentation.pairlist

sealed class PairListUiEvent {
    data class NavigateToPairChart(
        val pairNormalized: String
    ) : PairListUiEvent()
}
