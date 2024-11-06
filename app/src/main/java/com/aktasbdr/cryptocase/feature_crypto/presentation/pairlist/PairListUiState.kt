package com.aktasbdr.cryptocase.feature_crypto.presentation.pairlist

import com.aktasbdr.cryptocase.feature_crypto.domain.model.Ticker
import com.aktasbdr.cryptocase.feature_crypto.presentation.pairlist.FavoriteListAdapter.FavoriteListItem
import com.aktasbdr.cryptocase.feature_crypto.presentation.pairlist.PairListAdapter.PairListItem

data class PairListUiState(
    val isShimmerVisible: Boolean = false,
    val favoriteItems: List<FavoriteListItem> = emptyList(),
    val pairItems: List<PairListItem> = emptyList()
) {

    val tickers: List<Ticker>
        get() = pairItems.map { it.ticker }

    val isFavoriteLayoutVisible: Boolean
        get() = favoriteItems.isNotEmpty()

    fun isFavorite(pairName: String): Boolean {
        return favoriteItems.any { it.favorite.pairName == pairName }
    }
}
