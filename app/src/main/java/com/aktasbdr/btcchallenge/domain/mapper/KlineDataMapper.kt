package com.aktasbdr.btcchallenge.domain.mapper

import com.aktasbdr.btcchallenge.data.model.KlineDataResponse
import com.aktasbdr.btcchallenge.domain.model.KlineData
import com.aktasbdr.btcchallenge.utils.Mapper
import javax.inject.Inject

class KlineDataMapper @Inject constructor() : Mapper<KlineDataResponse, KlineData> {

    override fun map(input: KlineDataResponse): KlineData = with(input) {
        return KlineData(
            timestamp = timestamp.orEmpty(),
            close = close.orEmpty()
        )
    }
}
