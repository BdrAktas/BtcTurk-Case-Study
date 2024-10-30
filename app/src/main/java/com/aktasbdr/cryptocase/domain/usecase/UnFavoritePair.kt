package com.aktasbdr.cryptocase.domain.usecase

import com.aktasbdr.cryptocase.data.repository.FavoriteRepository
import com.aktasbdr.cryptocase.domain.model.Ticker
import javax.inject.Inject

class UnFavoritePair @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    suspend operator fun invoke(ticker: Ticker) = with(ticker) {
        favoriteRepository.unFavoritePair(pairNormalized)
    }
}
