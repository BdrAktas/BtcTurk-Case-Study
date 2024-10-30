package com.aktasbdr.btcchallenge.data.repository

import com.aktasbdr.btcchallenge.data.exception.HandleException
import com.aktasbdr.btcchallenge.data.model.TickersResponse.TickerResponse
import com.aktasbdr.btcchallenge.data.service.CommonService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommonRepository @Inject constructor(
    private val commonService: CommonService,
    private val handleException: HandleException
) {

    suspend fun getTickers(pairSymbol: String): List<TickerResponse> {
        return runCatching {
            commonService.getTickers(pairSymbol).data.orEmpty()
        }.getOrElse { throw handleException(it) }
    }
}
