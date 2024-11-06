package com.aktasbdr.cryptocase.feature_crypto.data.mappers

import com.aktasbdr.cryptocase.core.domain.extensions.Mapper
import com.aktasbdr.cryptocase.core.presentation.extensions.orZero
import com.aktasbdr.cryptocase.feature_crypto.data.remote.dto.CoinsResponse.CoinResponse
import com.aktasbdr.cryptocase.feature_crypto.domain.model.Ticker
import javax.inject.Inject

class TickerMapper @Inject constructor() : Mapper<CoinResponse, Ticker> {

    override fun map(input: CoinResponse): Ticker = with(input) {
        return Ticker(
            pair = pair.orEmpty(),
            pairNormalized = pairNormalized.orEmpty(),
            last = last.orZero(),
            volume = volume.orZero(),
            dailyPercent = dailyPercent.orZero(),
            numeratorSymbol = numeratorSymbol.orEmpty(),
        )
    }
}
