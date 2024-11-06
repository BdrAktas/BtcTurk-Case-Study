package com.aktasbdr.cryptocase.feature_crypto.data.mappers

import com.aktasbdr.cryptocase.core.domain.extensions.Mapper
import com.aktasbdr.cryptocase.feature_crypto.data.local.model.FavoriteEntity
import com.aktasbdr.cryptocase.feature_crypto.domain.model.Ticker
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
