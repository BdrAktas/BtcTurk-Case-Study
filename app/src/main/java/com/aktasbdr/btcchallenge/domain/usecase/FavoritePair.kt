package com.aktasbdr.btcchallenge.domain.usecase

import com.aktasbdr.btcchallenge.data.repository.FavoriteRepository
import com.aktasbdr.btcchallenge.domain.mapper.PairMapper
import com.aktasbdr.btcchallenge.domain.model.Ticker
import com.aktasbdr.btcchallenge.utils.mapWith
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
