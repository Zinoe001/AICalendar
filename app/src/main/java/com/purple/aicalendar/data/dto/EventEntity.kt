package com.purple.aicalendar.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a single note entity in the database.
 *
 * This data class is used by Room to define the 'note_model' table structure.
 *
 * @property uid The unique identifier for the note. This is the primary key and is auto-generated.
 * @property title The title of the note. Can be null.
 * @property date A string representation of the date the note was created or last modified. Can be null.
 */
@Entity(tableName = "event_model")
data class EventEntity (
    @PrimaryKey(autoGenerate = false) val uid: String,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "amount") val amount: String?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "transactionType") val transactionType: String?,
    @ColumnInfo(name = "accuracy") val accuracy: String?,
    @ColumnInfo(name = "obligee") val obligee: String?,
    @ColumnInfo(name = "accountNumber") val accountNumber: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "pendingDelete") val pendingDelete: Boolean = false // new column
)