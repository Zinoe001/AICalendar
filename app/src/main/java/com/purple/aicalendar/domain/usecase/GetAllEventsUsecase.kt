package com.purple.aicalendar.domain.usecase

import com.purple.aicalendar.domain.repository.EventsRepository
import javax.inject.Inject

class GetAllEventsUsecase @Inject constructor (private val eventsRepository: EventsRepository) {

    suspend fun getTodayEvents() = eventsRepository.getTodayEvents()
    suspend fun getUpcomingEvents() = eventsRepository.getUpcomingEvents()

    suspend fun getAllEvents() = eventsRepository.getAllEvents()

    suspend fun deleteAllEvents() = eventsRepository.deleteAllEvents()
    suspend fun deleteAllPendingEvents()= eventsRepository.deleteAllPendingEvents()


    suspend fun markEventsAsPending() = eventsRepository.markEventsAsPending()
    suspend fun editEvents(
        id: String,
        amount: String,
        title: String,
        date: String,
        transactionType: String,
        name: String,
        number: String,
        description: String) = eventsRepository.editEvents(
        id=id,
        amount=amount,
        title = title,
        date=date,
        transactionType=transactionType,
        name=name,
        number=number,
        description=description
        )

}