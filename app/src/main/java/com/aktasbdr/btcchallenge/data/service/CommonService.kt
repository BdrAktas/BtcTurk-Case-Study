package com.aktasbdr.btcchallenge.data.service

import com.aktasbdr.btcchallenge.data.model.TickersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CommonService {

    @GET("v2/ticker")
    suspend fun getTickers(
        @Query("pairSymbol") pairSymbol: String
    ): TickersResponse
}
