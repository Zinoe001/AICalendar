package com.purple.aicalendar.domain.usecase

import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.domain.repository.TaskRepository
import javax.inject.Inject

class GetAllTaskUsecase @Inject constructor (private val taskRepository: TaskRepository) {

    suspend fun getPredictions(month: Int, year: Int) = taskRepository.getPredictions(month= month, year= year)
    suspend fun postEvents(kept:List<Event>,discarded: List<Event>) = taskRepository.postEvents(kept,discarded)

    suspend fun saveEvents(kept:List<Event>,discarded: List<Event>,predictions: List<Event>) = taskRepository.saveEvents(kept= kept,discarded= discarded,predictions=predictions)
    suspend fun getKeptEvents() = taskRepository.getKeptEvents()
    suspend fun getDiscardedEvents() = taskRepository.getDiscardedEvents()
    suspend fun getPredictionEvents() = taskRepository.getPredictionEvents()
    suspend fun saveRemainingEvents(events: List<Event>) = taskRepository.saveRemainingEvents(events= events)
}