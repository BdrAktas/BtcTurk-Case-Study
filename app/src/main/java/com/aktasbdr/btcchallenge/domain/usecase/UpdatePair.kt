package com.aktasbdr.btcchallenge.domain.usecase

import com.aktasbdr.btcchallenge.data.repository.FavoriteRepository
import com.aktasbdr.btcchallenge.domain.mapper.PairMapper
import com.aktasbdr.btcchallenge.domain.model.Ticker
import com.aktasbdr.btcchallenge.utils.mapWith
import javax.inject.Inject

class UpdatePair @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val pairMapper: PairMapper
) {

    suspend operator fun invoke(ticker: List<Ticker>) = with(ticker) {
        val favoriteEntities = ticker.map { it.mapWith(pairMapper) }
        favoriteRepository.updatePair(favoriteEntities)
    }
}
