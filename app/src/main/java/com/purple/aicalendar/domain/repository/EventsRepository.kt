package com.purple.aicalendar.domain.repository

import com.purple.aicalendar.domain.models.Event

interface EventsRepository {
    //API Calls
    suspend fun checkDevice(): Result<Boolean>
    suspend fun registerDevice(token: String)
    //Database Calls
    suspend fun getTodayEvents(): Result<List<Event>>
    suspend fun getUpcomingEvents(): Result<List<Event>>
    suspend fun getAllEvents(): Result<List<Event>>
    suspend fun deleteAllEvents()
    suspend fun deleteAllPendingEvents()

    suspend fun markEventsAsPending()
    suspend fun editEvents(
        id: String,
        title: String,
        amount: String,
        date: String,
        transactionType: String,
        name: String,
        number: String,
        description: String,
        isCalender: Boolean
    ): Result<Boolean>
}