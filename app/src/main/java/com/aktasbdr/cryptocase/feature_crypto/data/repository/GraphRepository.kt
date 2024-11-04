package com.aktasbdr.cryptocase.feature_crypto.data.repository

import com.aktasbdr.cryptocase.core.data.util.HandleException
import com.aktasbdr.cryptocase.feature_crypto.data.model.KlineDataResponse
import com.aktasbdr.cryptocase.feature_crypto.data.service.GraphService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GraphRepository @Inject constructor(
    private val graphService: GraphService,
    private val handleException: HandleException
) {

    suspend fun getKlineData(
        from: Long,
        resolution: Int,
        symbol: String,
        to: Long
    ): KlineDataResponse {
        return runCatching {
            graphService.getKlineData(from, resolution, symbol, to)
        }.getOrElse { throw handleException(it) }
    }
}
