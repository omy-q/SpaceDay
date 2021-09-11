package com.example.spaceday.supermodel.local

import androidx.room.*

@Dao
interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: FavoriteEntity)

    @Query("SELECT * FROM FavoriteEntity")
    fun all(): List<FavoriteEntity>

    @Delete()
    fun delete(entity: FavoriteEntity)
}