package com.aktasbdr.cryptocase.data.service

import com.aktasbdr.cryptocase.data.model.TickersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CommonService {

    @GET("v2/ticker")
    suspend fun getTickers(
        @Query("pairSymbol") pairSymbol: String
    ): TickersResponse
}
