package com.purple.aicalendar.data.repository

import android.util.Log
import androidx.compose.ui.platform.LocalGraphicsContext
import com.purple.aicalendar.BuildConfig
import com.purple.aicalendar.data.api.AICalenderApiServices
import com.purple.aicalendar.data.dao.EventDao
import com.purple.aicalendar.data.mapper.toEntity
import com.purple.aicalendar.data.mapper.toEvent
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.domain.repository.CalendarRepository
import javax.inject.Inject
import kotlin.collections.flatMap
import kotlin.collections.isNotEmpty

class CalendarRepositoryImpl @Inject constructor (
    private val eventDao: EventDao,
    private val apiService: AICalenderApiServices
) : CalendarRepository {
    override suspend fun getAllEvents(): Result<List<Event>> {
        return try {
            val response = apiService.getCalendar(id = BuildConfig.USER_1)
            val events =  if (response.isNotEmpty()) {
                response.flatMap { it.items }.map { it.toEvent() }
            } else {
                emptyList()
            }
            Log.d("CalendarRepositoryImpl", "response: ${events.size}")
            // 4️⃣ INSERT ALL EVENTS
            eventDao.insertEvents(events.map { it.toEntity() })
            // 4️⃣ QUERY ONLY ALL EVENTS
            val allEvent =  eventDao.getAllEvents()
            Log.d("CalendarRepositoryImpl", "getAllEvents: ${allEvent.size}")
            Result.success(allEvent.map {it.toEvent()})
        } catch (e: Exception) {
            Log.e("CalendarRepositoryImpl", "Exception", e)
            Result.failure(e)
        }
    }

}