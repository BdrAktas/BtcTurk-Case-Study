package com.aktasbdr.btcchallenge.domain.usecase

import com.aktasbdr.btcchallenge.data.repository.FavoriteRepository
import com.aktasbdr.btcchallenge.domain.model.Ticker
import javax.inject.Inject

class UnFavoritePair @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    suspend operator fun invoke(ticker: Ticker) = with(ticker) {
        favoriteRepository.unFavoritePair(pairNormalized)
    }
}
