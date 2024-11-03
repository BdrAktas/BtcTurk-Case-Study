package com.aktasbdr.cryptocase.presentation.pairlist

import com.aktasbdr.cryptocase.domain.model.Ticker
import com.aktasbdr.cryptocase.presentation.pairlist.FavoriteListAdapter.FavoriteListItem
import com.aktasbdr.cryptocase.presentation.pairlist.PairListAdapter.PairListItem

data class PairListUiState(
    val favoriteItems: List<FavoriteListItem> = emptyList(),
    val pairItems: List<PairListItem> = emptyList(),
    val tickers: List<Ticker> = emptyList(),
    val isLoading: Boolean = false,
    val isFavoriteLayoutVisible: Boolean = false
) {
    fun isFavorite(pairNormalized: String): Boolean {
        return favoriteItems.any { it.favorite.pairName == pairNormalized }
    }
}
