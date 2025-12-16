package com.purple.aicalendar.data.repository

import android.util.Log
import com.purple.aicalendar.BuildConfig
import com.purple.aicalendar.core.utils.AICalendarPreference
import com.purple.aicalendar.data.api.AICalenderApiServices
import com.purple.aicalendar.data.dao.EventDao
import com.purple.aicalendar.data.mapper.toEntity
import com.purple.aicalendar.data.mapper.toEvent
import com.purple.aicalendar.domain.repository.TaskRepository
import javax.inject.Inject
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.domain.models.EventsRequestBody

class TaskRepositoryImpl  @Inject constructor(
    private val aiCalendarPreference: AICalendarPreference,
    private val eventDao: EventDao,
    private val apiService: AICalenderApiServices
) : TaskRepository {

    override suspend fun getPredictions( month: Int, year: Int): Result<List<Event>> {
        return try {
            val response = apiService.getEvents(id= BuildConfig.USER_2, year =  year,month= month)
//            val response = apiService.getItemsLimit(id= BuildConfig.USER_4)
            val predictions = if (response.isNotEmpty()) {
                    response.flatMap { it.items }.map { it.toEvent() }
                } else {
                    emptyList()
                }
            Result.success(predictions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun postEvents(kept: List<Event>,discarded: List<Event>): Result<Boolean>{
        return try {
            val body = EventsRequestBody(
                acceptedItemIds = kept.map { it.id },
                rejectedItemIds = discarded.map { it.id }
            )
            Log.d("TaskRepositoryImpl", "Body: $body")
            val response = apiService.postEvents(request = body)
            if (response.code()==200) {
                Log.d("TaskRepositoryImpl", "Success: ${response.code()}")
                Result.success(true)
            } else {
                Log.d("TaskRepositoryImpl", "Error: ${response.code()}")
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("TaskRepositoryImpl", "Exception", e)
            Result.failure(e)
        }
    }

    override suspend fun saveEvents(kept:List<Event>,discarded: List<Event>,predictions: List<Event>) {
        aiCalendarPreference.saveList(kept = kept,discarded=discarded,predictions=predictions)
    }

    override suspend fun getKeptEvents():List<Event>{
        return aiCalendarPreference.getKeptList()
    }
    override suspend fun getDiscardedEvents():List<Event>{
        return aiCalendarPreference.getDiscardList()
    }

    override suspend fun getPredictionEvents():List<Event>{
        return aiCalendarPreference.getPredictionList()
    }

    override suspend fun saveRemainingEvents(events: List<Event>){
        try {
            eventDao.insertEvents(events.map{it.toEntity()})
//            Result.success(allEvent.map {it.toEvent()})
        } catch (_: Exception) {
//            Result.failure(e)
        }
    }

}