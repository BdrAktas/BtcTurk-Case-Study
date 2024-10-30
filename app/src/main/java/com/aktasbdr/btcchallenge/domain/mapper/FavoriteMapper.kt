package com.aktasbdr.btcchallenge.domain.mapper

import com.aktasbdr.btcchallenge.data.database.FavoriteEntity
import com.aktasbdr.btcchallenge.domain.model.Favorite
import com.aktasbdr.btcchallenge.utils.Mapper
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
