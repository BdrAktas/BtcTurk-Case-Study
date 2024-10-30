package com.aktasbdr.cryptocase.domain.usecase

import com.aktasbdr.cryptocase.data.repository.FavoriteRepository
import com.aktasbdr.cryptocase.domain.mapper.FavoriteMapper
import com.aktasbdr.cryptocase.domain.model.Favorite
import com.aktasbdr.cryptocase.utils.mapWith
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
