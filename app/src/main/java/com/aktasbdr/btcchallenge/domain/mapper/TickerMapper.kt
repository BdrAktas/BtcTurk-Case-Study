package com.aktasbdr.btcchallenge.domain.mapper

import com.aktasbdr.btcchallenge.data.model.TickersResponse.TickerResponse
import com.aktasbdr.btcchallenge.domain.model.Ticker
import com.aktasbdr.btcchallenge.utils.Mapper
import com.aktasbdr.btcchallenge.utils.orZero
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
