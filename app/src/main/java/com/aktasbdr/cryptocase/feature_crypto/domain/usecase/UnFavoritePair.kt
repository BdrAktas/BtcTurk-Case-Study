package com.aktasbdr.cryptocase.feature_crypto.domain.usecase

import com.aktasbdr.cryptocase.feature_crypto.data.repository.FavoriteRepository
import com.aktasbdr.cryptocase.feature_crypto.domain.model.Ticker
import javax.inject.Inject

class UnFavoritePair @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    suspend operator fun invoke(ticker: Ticker) = with(ticker) {
        favoriteRepository.unFavoritePair(pairNormalized)
    }
}
