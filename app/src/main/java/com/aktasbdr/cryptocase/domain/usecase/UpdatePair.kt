package com.aktasbdr.cryptocase.domain.usecase

import com.aktasbdr.cryptocase.data.repository.FavoriteRepository
import com.aktasbdr.cryptocase.domain.mapper.PairMapper
import com.aktasbdr.cryptocase.domain.model.Ticker
import com.aktasbdr.cryptocase.core.domain.mapper.mapWith
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
