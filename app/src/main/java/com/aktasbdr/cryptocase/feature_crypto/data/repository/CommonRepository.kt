package com.aktasbdr.cryptocase.feature_crypto.data.repository

import com.aktasbdr.cryptocase.R
import com.aktasbdr.cryptocase.core.data.remote.NetworkResult
import com.aktasbdr.cryptocase.core.data.remote.SafeApiCall
import com.aktasbdr.cryptocase.feature_crypto.data.remote.dto.CoinsResponse.CoinResponse
import com.aktasbdr.cryptocase.feature_crypto.data.remote.service.CommonService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommonRepository @Inject constructor(
    private val commonService: CommonService,
    private val safeApiCall: SafeApiCall
) {
    suspend fun getTickers(pairSymbol: String): NetworkResult<List<CoinResponse>> {

        return safeApiCall {
            val response = commonService.getTickers(pairSymbol)

            if (!response.success!!) {
                throw Exception(response.message ?: R.string.error_unknown.toString())
            }
            response.data ?: throw Exception(response.message ?: R.string.no_data.toString())
        }
    }
}