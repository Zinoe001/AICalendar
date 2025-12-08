package com.purple.aicalendar.domain.repository

import com.purple.aicalendar.domain.models.Event

interface EventsRepository {
    suspend fun getTodayEvents(): Result<List<Event>>
    suspend fun getUpcomingEvents(): Result<List<Event>>
    suspend fun getAllEvents(): Result<List<Event>>
    suspend fun deleteAllEvents()
    suspend fun editEvents(
        id: String,
        title: String,
        amount: String,
        date: String,
        transactionType: String,
        name: String,
        number: String,
        description: String,
    ): Result<Boolean>
}