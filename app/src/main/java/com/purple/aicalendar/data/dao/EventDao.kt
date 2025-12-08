package com.purple.aicalendar.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.purple.aicalendar.data.dto.EventEntity

/**
 * Data Access Object (DAO) for the [EventEntity] entity.
 * This interface defines the database interactions for notes, including
 * inserting, deleting, updating, and retrieving notes from the database.
 */
@Dao
interface EventDao {
    /**
     * Inserts a note into the database. If a note with the same primary key already exists,
     * it will be replaced.
     *
     * @param events The [EventEntity] object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)
    /**
     * Deletes a note from the database.
     * @param event The [EventEntity] to be deleted.
     */
    @Delete
    suspend fun deleteEvent(event: EventEntity)
    /**
     * Retrieves all notes from the database.
     *
     * @return A [List] of [EventEntity] objects.
     */
    @Query("SELECT * FROM event_model")
    suspend fun getAllEvents(): List<EventEntity>

    @Query("SELECT * FROM event_model WHERE date =:today")
    suspend fun getEventsForDate(today:String): List<EventEntity>
    /**
     * Updates an existing note in the database.
     *
     * @param uid The unique ID of the note to update.
     * @param title The new title for the note.
     * @param date The new date string for the note.
     */
    @Query("UPDATE event_model SET title = :title,amount=:amount,billName=:billName,description=:description,date = :date WHERE uid = :uid")
    suspend fun updateBillEvent(
        uid: String,
        title: String,
        date:String,
        amount:String,
        billName:String,
        description:String,
        )

    @Query("UPDATE event_model SET title = :title,amount=:amount,obligee=:obligee,accountNumber=:accountNumber,description=:description,date = :date WHERE uid = :uid")
    suspend fun updateTransactionEvent(
        uid: String,
        title: String,
        date:String,
        amount:String,
        obligee:String,
        accountNumber:String,
        description:String,
    )
    /**
     * Deletes all notes from the database.
     */
    @Query("DELETE FROM event_model")
    suspend fun deleteAllEvents()

    @Query("DELETE FROM event_model WHERE pendingDelete = 1")
    suspend fun deletePendingEvents()

    @Query("UPDATE event_model SET pendingDelete = 1")
    suspend fun markAllPendingDelete()

//    @Query("SELECT * FROM event_model WHERE pendingDelete = 1")
//    suspend fun getPendingDeleteEvents(): List<EventEntity>
}