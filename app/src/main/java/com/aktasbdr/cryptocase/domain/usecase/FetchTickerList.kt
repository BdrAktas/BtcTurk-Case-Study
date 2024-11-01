package com.aktasbdr.cryptocase.domain.usecase

import com.aktasbdr.cryptocase.data.repository.CommonRepository
import com.aktasbdr.cryptocase.domain.mapper.TickerMapper
import com.aktasbdr.cryptocase.domain.model.Ticker
import com.aktasbdr.cryptocase.core.domain.mapper.mapWith
import javax.inject.Inject

class FetchTickerList @Inject constructor(
    private val commonRepository: CommonRepository,
    private val tickerMapper: TickerMapper,
    private val updatePair: UpdatePair
) {

    suspend operator fun invoke(pairSymbol: String = ""): List<Ticker> {
        val response = commonRepository.getTickers(pairSymbol)
        return response.map { it.mapWith(tickerMapper) }
            .also { updatePair(it) }
    }
}
