package com.aktasbdr.cryptocase.feature_crypto.domain.model

data class KlineData(
    val timestamp: List<Long>,
    val close: List<Double>
)
