package com.purple.aicalendar.domain.usecase

import com.purple.aicalendar.domain.repository.CalendarRepository
import javax.inject.Inject

class GetAllCalendarUsecase  @Inject constructor (private val calendarRepository: CalendarRepository)  {

    suspend fun getAllEvents()= calendarRepository.getAllEvents()
}