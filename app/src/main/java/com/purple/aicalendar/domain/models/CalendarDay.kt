package com.purple.aicalendar.domain.models

import java.time.LocalDate

data class CalendarDay(
    val date: LocalDate,
    val isToday: Boolean = false,
    val isSelected: Boolean = false,
    val isFromCurrentMonth: Boolean = true
)