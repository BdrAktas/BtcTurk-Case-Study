package com.aktasbdr.cryptocase.feature_crypto.domain.usecase

import com.aktasbdr.cryptocase.core.data.remote.NetworkResult
import com.aktasbdr.cryptocase.feature_crypto.data.repository.CommonRepository
import com.aktasbdr.cryptocase.feature_crypto.domain.mapper.TickerMapper
import com.aktasbdr.cryptocase.feature_crypto.domain.model.Ticker
import com.aktasbdr.cryptocase.core.domain.extensions.mapWith
import javax.inject.Inject

class FetchTickerList @Inject constructor(
    private val commonRepository: CommonRepository,
    private val tickerMapper: TickerMapper,
    private val updatePair: UpdatePair
) {
    suspend operator fun invoke(pairSymbol: String = ""): NetworkResult<List<Ticker>> {
        println("FetchTickerList - invoke called with symbol: $pairSymbol") // Debug log

        return when (val result = commonRepository.getTickers(pairSymbol)) {
            is NetworkResult.Success -> {
                println("FetchTickerList - Repository success with ${result.data.size} items") // Debug log

                val mappedTickers = result.data.map {
                    println("FetchTickerList - Mapping ticker: $it") // Debug log
                    it.mapWith(tickerMapper)
                }
                println("FetchTickerList - Mapped ${mappedTickers.size} tickers") // Debug log

                updatePair(mappedTickers)
                println("FetchTickerList - UpdatePair completed") // Debug log

                NetworkResult.Success(mappedTickers)
            }
            is NetworkResult.Error -> {
                println("FetchTickerList - Error: ${result.exception.message}") // Debug log
                NetworkResult.Error(result.exception)
            }
            NetworkResult.Loading -> {
                println("FetchTickerList - Loading") // Debug log
                NetworkResult.Loading
            }
        }
    }
}