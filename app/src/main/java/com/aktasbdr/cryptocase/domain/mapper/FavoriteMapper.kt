package com.aktasbdr.cryptocase.domain.mapper

import com.aktasbdr.cryptocase.data.database.FavoriteEntity
import com.aktasbdr.cryptocase.domain.model.Favorite
import com.aktasbdr.cryptocase.core.domain.mapper.Mapper
import javax.inject.Inject

class FavoriteMapper @Inject constructor() : Mapper<FavoriteEntity, Favorite> {

    override fun map(input: FavoriteEntity): Favorite = with(input) {
        return Favorite(
            pairName = pairName,
            last = last,
            dailyPercent = dailyPercent,
        )
    }
}
