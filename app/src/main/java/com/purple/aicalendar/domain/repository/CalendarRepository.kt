package com.purple.aicalendar.domain.repository

import com.purple.aicalendar.domain.models.Event

interface CalendarRepository {

    suspend fun getAllEvents(): Result<List<Event>>
}