package com.purple.aicalendar.data.repository

import android.util.Log
import com.purple.aicalendar.data.api.AICalenderApiServices
import com.purple.aicalendar.data.dao.EventDao
import com.purple.aicalendar.data.mapper.toEvent
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.domain.repository.EventsRepository
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class EventsRepositoryImpl  @Inject constructor (
    private val eventDao: EventDao,
    private val apiService: AICalenderApiServices
) : EventsRepository {

    override suspend fun getTodayEvents(): Result<List<Event>> {
        return try {
            val today = LocalDate.now(ZoneId.of("UTC")).toString()
            // 4️⃣ QUERY ONLY TODAY'S EVENTS
            val todayEvents = eventDao.getEventsForDate(today)
            Log.d("EventsRepositoryImpl", "getTodayEvents: $todayEvents")
            Result.success(todayEvents.map {it.toEvent()})
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUpcomingEvents(): Result<List<Event>> {
        return try {
            // 3️⃣ GET TODAY'S DATE // "2025-11-30"
            val tomorrow = LocalDate.now(ZoneId.of("UTC")).plusDays(1).toString()
            // 4️⃣ QUERY ONLY TODAY'S EVENTS
            val tomorrowEvents = eventDao.getEventsForDate(tomorrow)
            Log.d("EventsRepositoryImpl", "getTomorrowEvents: $tomorrowEvents")
            Result.success(tomorrowEvents.map {it.toEvent()})
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllEvents(): Result<List<Event>> {
        return try {
            // 4️⃣ QUERY ONLY ALL EVENTS
            val allEvent =  eventDao.getAllEvents()
            Log.d("EventsRepositoryImpl", "getAllEvents: $allEvent")
            Result.success(allEvent.map {it.toEvent()})
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun deleteAllEvents() {
        eventDao.deleteAllEvents()
    }

    override suspend fun deleteAllPendingEvents(){
        eventDao.deletePendingEvents()
    }
    override suspend fun markEventsAsPending(){
        try {
            eventDao.markAllPendingDelete()
//            Result.success(allEvent.map {it.toEvent()})
        } catch (_: Exception) {
//            Result.failure(e)
        }
    }

    override suspend fun editEvents(
        id: String,
        title: String,
        amount: String,
        date: String,
        transactionType: String,
        name: String,
        number: String,
        description: String): Result<Boolean> {
        Log.d("EventsRepositoryImpl", "editEvents: $amount,$date,$transactionType,$name,$number,$description")

        return try {
            if(transactionType=="Bill") {
                eventDao.updateBillEvent(
                    uid=id,
                    amount=amount,
                    date=date,
                    billName = name,
                    title = title,
                    description = description,
                )
            }
            else{
                eventDao.updateTransactionEvent(
                    uid=id,
                    amount=amount,
                    date=date,
                    obligee = name,
                    accountNumber = number,
                    title = title,
                    description = description,
                )
            }
            Result.success(true)
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

}