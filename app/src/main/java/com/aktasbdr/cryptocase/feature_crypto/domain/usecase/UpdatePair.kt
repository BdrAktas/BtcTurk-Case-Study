package com.aktasbdr.cryptocase.feature_crypto.domain.usecase

import com.aktasbdr.cryptocase.feature_crypto.data.repository.FavoriteRepository
import com.aktasbdr.cryptocase.feature_crypto.domain.mapper.PairMapper
import com.aktasbdr.cryptocase.feature_crypto.domain.model.Ticker
import com.aktasbdr.cryptocase.core.domain.extensions.mapWith
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