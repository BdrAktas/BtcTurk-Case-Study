package com.aktasbdr.cryptocase.feature_crypto.domain.usecase

import com.aktasbdr.cryptocase.core.data.remote.NetworkResult
import com.aktasbdr.cryptocase.core.domain.extensions.mapWith
import com.aktasbdr.cryptocase.feature_crypto.data.mappers.TickerMapper
import com.aktasbdr.cryptocase.feature_crypto.data.repository.CommonRepository
import com.aktasbdr.cryptocase.feature_crypto.domain.model.Ticker
import javax.inject.Inject

class FetchTickerList @Inject constructor(
    private val commonRepository: CommonRepository,
    private val tickerMapper: TickerMapper,
    private val updatePair: UpdatePair
) {
    suspend operator fun invoke(pairSymbol: String = ""): NetworkResult<List<Ticker>> {

        return when (val result = commonRepository.getTickers(pairSymbol)) {
            is NetworkResult.Success -> {

                val mappedTickers = result.data.map {
                    it.mapWith(tickerMapper)
                }

                updatePair(mappedTickers)

                NetworkResult.Success(mappedTickers)
            }

            is NetworkResult.Error -> {
                NetworkResult.Error(result.exception)
            }

            NetworkResult.Loading -> {
                NetworkResult.Loading
            }
        }
    }
}