package com.aktasbdr.cryptocase.domain.mapper

import com.aktasbdr.cryptocase.data.model.TickersResponse.TickerResponse
import com.aktasbdr.cryptocase.domain.model.Ticker
import com.aktasbdr.cryptocase.core.domain.mapper.Mapper
import com.aktasbdr.cryptocase.core.presentation.extensions.orZero
import javax.inject.Inject

class TickerMapper @Inject constructor() : Mapper<TickerResponse, Ticker> {

    override fun map(input: TickerResponse): Ticker = with(input) {
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
