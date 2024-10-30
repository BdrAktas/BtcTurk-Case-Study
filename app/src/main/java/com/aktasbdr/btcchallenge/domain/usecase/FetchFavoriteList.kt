package com.aktasbdr.btcchallenge.domain.usecase

import com.aktasbdr.btcchallenge.data.repository.FavoriteRepository
import com.aktasbdr.btcchallenge.domain.mapper.FavoriteMapper
import com.aktasbdr.btcchallenge.domain.model.Favorite
import com.aktasbdr.btcchallenge.utils.mapWith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchFavoriteList @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val favoriteMapper: FavoriteMapper
) {

    operator fun invoke(): Flow<List<Favorite>> {
        val entities = favoriteRepository.getAllFavorites()
        return entities.map { it.map { entity -> entity.mapWith(favoriteMapper) } }
    }
}
