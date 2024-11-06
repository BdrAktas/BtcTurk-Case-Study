package com.aktasbdr.cryptocase.feature_crypto.domain.usecase

import com.aktasbdr.cryptocase.core.data.mapper.toEpochSecond
import com.aktasbdr.cryptocase.core.data.remote.NetworkResult
import com.aktasbdr.cryptocase.core.data.remote.SafeApiCall
import com.aktasbdr.cryptocase.core.domain.extensions.mapWith
import com.aktasbdr.cryptocase.feature_crypto.data.mappers.KlineDataMapper
import com.aktasbdr.cryptocase.feature_crypto.data.repository.GraphRepository
import com.aktasbdr.cryptocase.feature_crypto.domain.model.KlineData
import java.util.Calendar
import java.util.Calendar.DATE
import java.util.Date
import javax.inject.Inject

class FetchKlineData @Inject constructor(
    private val graphRepository: GraphRepository,
    private val klineDataMapper: KlineDataMapper,
    private val safeApiCall: SafeApiCall
) {
    suspend operator fun invoke(symbol: String): NetworkResult<KlineData> {
        return safeApiCall {
            val toDate = Date()
            val fromDate = Calendar.getInstance().apply {
                time = toDate
                add(DATE, -ONE_DAY)
            }

            val response = graphRepository.getKlineData(
                from = fromDate.time.toEpochSecond(),
                resolution = RESOLUTION,
                symbol = symbol,
                to = toDate.toEpochSecond()
            )
            response.mapWith(klineDataMapper)
        }
    }

    companion object {
        private const val ONE_DAY = 1
        private const val RESOLUTION = 60
    }
}
