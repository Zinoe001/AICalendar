package com.purple.aicalendar.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.purple.aicalendar.data.dao.EventDao
import com.purple.aicalendar.data.dto.EventEntity


/**
 * The Room database for this app.
 *
 * This class defines the database configuration and serves as the main access point to the
 * persisted data. It lists the entities that belong to the database and provides DAOs
 * for accessing them.
 *
 * @property eventDao Provides access to the `Note` table.
 */
@Database(entities = [EventEntity::class], version = 1, exportSchema = false)
abstract class CalenderDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
}