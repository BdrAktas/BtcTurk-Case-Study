package com.aktasbdr.cryptocase.feature_crypto.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aktasbdr.cryptocase.feature_crypto.data.local.dao.FavoriteDao
import com.aktasbdr.cryptocase.feature_crypto.data.local.model.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
