package com.aktasbdr.cryptocase.domain.usecase

import com.aktasbdr.cryptocase.data.repository.FavoriteRepository
import com.aktasbdr.cryptocase.domain.mapper.PairMapper
import com.aktasbdr.cryptocase.domain.model.Ticker
import com.aktasbdr.cryptocase.core.domain.mapper.mapWith
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
