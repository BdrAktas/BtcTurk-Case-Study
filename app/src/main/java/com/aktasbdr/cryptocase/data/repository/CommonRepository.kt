package com.aktasbdr.cryptocase.data.repository

import com.aktasbdr.cryptocase.data.exception.HandleException
import com.aktasbdr.cryptocase.data.model.TickersResponse.TickerResponse
import com.aktasbdr.cryptocase.data.service.CommonService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommonRepository @Inject constructor(
    private val commonService: CommonService,
    private val handleException: HandleException
) {
    suspend fun getTickers(pairSymbol: String): List<TickerResponse> {
        return try {
            commonService.getTickers(pairSymbol).data.orEmpty()
        } catch (e: Exception) {
            throw handleException(e)
        }
    }
}
