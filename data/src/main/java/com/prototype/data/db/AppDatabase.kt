package com.prototype.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prototype.data.db.dao.DefinitionDao
import com.prototype.data.db.model.DefinitionEntity

@Database(
    entities = [
        DefinitionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): DefinitionDao

}