package com.aktasbdr.cryptocase.ui.pairlist

sealed class PairListUiEvent {
    data class NavigateToPairChart(
        val pairName: String
    ) : PairListUiEvent()
}
