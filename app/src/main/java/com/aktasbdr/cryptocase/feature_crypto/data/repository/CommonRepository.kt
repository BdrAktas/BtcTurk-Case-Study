package com.aktasbdr.cryptocase.feature_crypto.data.repository

import com.aktasbdr.cryptocase.core.data.remote.SafeApiCall
import com.aktasbdr.cryptocase.core.data.remote.NetworkResult
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
        println("Repository - getTickers called with symbol: $pairSymbol") // Debug log

        return safeApiCall {
            println("Repository - Making API call") // Debug log
            val response = commonService.getTickers(pairSymbol)
            println("Repository - API response: success=${response.success}, code=${response.code}") // Debug log

            if (!response.success!!) {
                println("Repository - API error: ${response.message}") // Debug log
                throw Exception(response.message ?: "Unknown error")
            }

            response.data.orEmpty().also {
                println("Repository - Returning ${it.size} tickers") // Debug log
            }
            response.data ?: throw Exception("No data found in response")
        }
    }
}