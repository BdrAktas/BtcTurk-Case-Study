package com.aktasbdr.cryptocase.feature_crypto.domain.mapper

import com.aktasbdr.cryptocase.feature_crypto.data.model.KlineDataResponse
import com.aktasbdr.cryptocase.feature_crypto.domain.model.KlineData
import com.aktasbdr.cryptocase.core.domain.extensions.Mapper
import javax.inject.Inject

class KlineDataMapper @Inject constructor() : Mapper<KlineDataResponse, KlineData> {

    override fun map(input: KlineDataResponse): KlineData = with(input) {
        return KlineData(
            timestamp = timestamp.orEmpty(),
            close = close.orEmpty()
        )
    }
}
