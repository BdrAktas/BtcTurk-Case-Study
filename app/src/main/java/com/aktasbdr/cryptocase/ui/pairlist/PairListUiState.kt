package com.aktasbdr.cryptocase.ui.pairlist

import com.aktasbdr.cryptocase.domain.model.Ticker
import com.aktasbdr.cryptocase.ui.pairlist.FavoriteListAdapter.FavoriteListItem
import com.aktasbdr.cryptocase.ui.pairlist.PairListAdapter.PairListItem

data class PairListUiState(
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
