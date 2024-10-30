package com.aktasbdr.btcchallenge.domain.mapper

import com.aktasbdr.btcchallenge.data.database.FavoriteEntity
import com.aktasbdr.btcchallenge.domain.model.Ticker
import com.aktasbdr.btcchallenge.utils.Mapper
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
