package com.aktasbdr.cryptocase.feature_crypto.presentation.pairchart

import com.github.mikephil.charting.data.Entry
import com.aktasbdr.cryptocase.feature_crypto.domain.model.KlineData

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
