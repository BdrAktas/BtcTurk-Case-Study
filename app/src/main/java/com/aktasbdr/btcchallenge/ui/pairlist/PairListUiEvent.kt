package com.aktasbdr.btcchallenge.ui.pairlist

sealed class PairListUiEvent {
    data class NavigateToPairChart(
        val pairName: String
    ) : PairListUiEvent()
}
