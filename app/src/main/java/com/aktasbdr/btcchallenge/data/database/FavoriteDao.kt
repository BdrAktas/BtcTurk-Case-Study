package com.aktasbdr.btcchallenge.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = IGNORE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Update
    suspend fun updateFavorite(favoriteEntities: List<FavoriteEntity>)

    @Query("DELETE FROM favorite WHERE pairName = :pairName")
    suspend fun deleteFavorite(pairName: String)
}
