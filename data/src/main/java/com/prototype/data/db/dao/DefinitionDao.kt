package com.prototype.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prototype.data.db.model.DefinitionEntity

@Dao
interface DefinitionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDefinitions(categories: List<DefinitionEntity>): LongArray

    @Query("DELETE FROM DefinitionEntity")
    suspend fun clearDefinitions(): Int

    @Query("SELECT * FROM DefinitionEntity WHERE word=:word")
    suspend fun definitionsFromWord(word: String): List<DefinitionEntity>

    @Query("SELECT * FROM DefinitionEntity WHERE word=:word ORDER BY thumbsUp DESC")
    suspend fun definitionsFromWordThumbsUp(word: String): List<DefinitionEntity>

    @Query("SELECT * FROM DefinitionEntity WHERE word=:word ORDER BY thumbsDown DESC")
    suspend fun definitionsFromWordThumbsDown(word: String): List<DefinitionEntity>
}