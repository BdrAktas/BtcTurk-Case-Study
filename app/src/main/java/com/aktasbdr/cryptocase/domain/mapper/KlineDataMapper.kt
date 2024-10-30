package com.aktasbdr.cryptocase.domain.mapper

import com.aktasbdr.cryptocase.data.model.KlineDataResponse
import com.aktasbdr.cryptocase.domain.model.KlineData
import com.aktasbdr.cryptocase.utils.Mapper
import javax.inject.Inject

class KlineDataMapper @Inject constructor() : Mapper<KlineDataResponse, KlineData> {

    override fun map(input: KlineDataResponse): KlineData = with(input) {
        return KlineData(
            timestamp = timestamp.orEmpty(),
            close = close.orEmpty()
        )
    }
}
