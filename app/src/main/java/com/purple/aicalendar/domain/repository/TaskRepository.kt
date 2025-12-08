package com.purple.aicalendar.domain.repository

import com.purple.aicalendar.domain.models.Event

interface TaskRepository {

    suspend fun getPredictions(): Result<List<Event>>

    suspend fun postEvents(kept: List<Event>,discarded: List<Event>): Result<Boolean>
    suspend fun saveEvents(kept:List<Event>,discarded: List<Event>,predictions: List<Event>)

    suspend fun getKeptEvents(): List<Event>
    suspend fun getDiscardedEvents(): List<Event>
    suspend fun getPredictionEvents(): List<Event>

}