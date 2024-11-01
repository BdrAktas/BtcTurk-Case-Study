package com.aktasbdr.cryptocase.presentation.pairchart

import com.github.mikephil.charting.data.Entry
import com.aktasbdr.cryptocase.domain.model.KlineData

data class PairChartUiState(
    val symbol: String = "",
    val klineData: KlineData? = null
) {

    val entries: List<Entry>
        get() {
            if (klineData == null) return emptyList()

            return klineData.timestamp.zip(klineData.close)
                .map { (x, y) -> Entry(x.toFloat(), y.toFloat()) }
        }
}
