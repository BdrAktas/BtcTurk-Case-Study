package com.aktasbdr.cryptocase.feature_crypto.data.remote.dto

import com.google.gson.annotations.SerializedName

data class KlineDataResponse(
    @SerializedName("s")
    val success: String?,
    @SerializedName("t")
    val timestamp: List<Long>?,
    @SerializedName("h")
    val high: List<Double>?,
    @SerializedName("o")
    val open: List<Double>?,
    @SerializedName("l")
    val low: List<Double>?,
    @SerializedName("c")
    val close: List<Double>?,
    @SerializedName("v")
    val volume: List<Double>?
)
