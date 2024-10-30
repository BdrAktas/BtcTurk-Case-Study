package com.aktasbdr.cryptocase.domain.model

data class KlineData(
    val timestamp: List<Long>,
    val close: List<Double>
)
