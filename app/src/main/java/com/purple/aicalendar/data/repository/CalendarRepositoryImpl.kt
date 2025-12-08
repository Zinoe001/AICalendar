package com.purple.aicalendar.data.repository

import android.util.Log
import com.purple.aicalendar.data.api.AICalenderApiServices
import com.purple.aicalendar.data.dao.EventDao
import com.purple.aicalendar.data.mapper.toEvent
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.domain.repository.CalendarRepository
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor (
    private val eventDao: EventDao,
    private val apiService: AICalenderApiServices
) : CalendarRepository {
    override suspend fun getAllEvents(): Result<List<Event>> {
        return try {
            // 4️⃣ QUERY ONLY ALL EVENTS
            val allEvent =  eventDao.getAllEvents()
            Log.d("EventsRepositoryImpl", "getAllEvents: ${allEvent.size}")
            Result.success(allEvent.map {it.toEvent()})
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}