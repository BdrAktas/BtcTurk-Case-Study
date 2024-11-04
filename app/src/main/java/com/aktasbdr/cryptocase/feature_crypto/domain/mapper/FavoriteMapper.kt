package com.aktasbdr.cryptocase.feature_crypto.domain.mapper

import com.aktasbdr.cryptocase.feature_crypto.data.database.FavoriteEntity
import com.aktasbdr.cryptocase.feature_crypto.domain.model.Favorite
import com.aktasbdr.cryptocase.core.domain.extensions.Mapper
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
