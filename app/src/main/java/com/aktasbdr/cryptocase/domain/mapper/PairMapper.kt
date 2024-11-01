package com.aktasbdr.cryptocase.domain.mapper

import com.aktasbdr.cryptocase.data.database.FavoriteEntity
import com.aktasbdr.cryptocase.domain.model.Ticker
import com.aktasbdr.cryptocase.core.domain.mapper.Mapper
import javax.inject.Inject

class PairMapper @Inject constructor() : Mapper<Ticker, FavoriteEntity> {

    override fun map(input: Ticker): FavoriteEntity = with(input) {
        return FavoriteEntity(
            pairName = pairNormalized,
            last = last,
            dailyPercent = dailyPercent
        )
    }
}
