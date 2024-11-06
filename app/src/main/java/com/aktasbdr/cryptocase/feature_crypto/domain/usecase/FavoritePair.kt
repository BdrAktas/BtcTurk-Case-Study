package com.aktasbdr.cryptocase.feature_crypto.domain.usecase

import com.aktasbdr.cryptocase.core.domain.extensions.mapWith
import com.aktasbdr.cryptocase.feature_crypto.data.mappers.PairMapper
import com.aktasbdr.cryptocase.feature_crypto.data.repository.FavoriteRepository
import com.aktasbdr.cryptocase.feature_crypto.domain.model.Ticker
import javax.inject.Inject

class FavoritePair @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val pairMapper: PairMapper
) {

    suspend operator fun invoke(ticker: Ticker) = with(ticker) {
        val favoriteEntity = ticker.mapWith(pairMapper)
        favoriteRepository.favoritePair(favoriteEntity)
    }
}
