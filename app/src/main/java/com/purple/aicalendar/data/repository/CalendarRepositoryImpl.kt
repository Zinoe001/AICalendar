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
            val response = apiService.getCalendar(id = BuildConfig.USER_4)

            if (!response.isSuccessful) {
                return Result.failure(
                    Exception("API Error: ${response.code()} ${response.message()}")
                )
            }

            val body = response.body()
                ?: return Result.failure(Exception("Empty response body"))

            val events = body.items.map{it.toEvent()}

            Log.d(
                "CalendarRepositoryImpl",
                "Success: ${events.size}"
            )

            // 1️⃣ Insert events into DB
            eventDao.insertEvents(events.map { it.toEntity() })

            // 2️⃣ Query events from DB
            val allEvents = eventDao.getAllEvents()

            Log.d(
                "CalendarRepositoryImpl",
                "getAllEvents: ${allEvents.size}"
            )

            // 3️⃣ Map entity → domain
            Result.success(allEvents.map { it.toEvent() })

        } catch (e: Exception) {
            Log.e("CalendarRepositoryImpl", "Exception", e)
            Result.failure(e)
        }
    }

}