package com.purple.aicalendar.ui.view.calendar

import com.purple.aicalendar.domain.models.Event
import java.time.LocalDate

data class CalendarState(
    val dateMatrix: List<List<LocalDate?>> = emptyList(),
    val selectedDate: LocalDate = LocalDate.now(),
    val isExpanded: Boolean = false,
    val eventsPerDay: Map<LocalDate, List<Event>> = emptyMap()
)
