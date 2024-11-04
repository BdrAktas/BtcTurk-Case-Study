package com.aktasbdr.cryptocase.feature_crypto.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey
    val pairName: String,
    val last: Double,
    val dailyPercent: Double
)
