package com.purple.aicalendar.ui.view.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.domain.usecase.GetAllCalendarUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calendarUseCase : GetAllCalendarUsecase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        CalendarState(
            selectedDate = LocalDate.now(),
            dateMatrix = generateMonthDates(LocalDate.now()),
            isExpanded = false
        )
    )
    val uiState: StateFlow<CalendarState> = _uiState.asStateFlow()
    private val _allEvents = MutableStateFlow<List<Event>>(emptyList())
    var allEvents = _allEvents.asStateFlow()

    fun getAllEvents(){
        viewModelScope.launch {
            delay(5000)
            calendarUseCase.getAllEvents()
                .onSuccess{ events ->
                    _allEvents.value = events
                    val map = events.groupBy { LocalDate.parse(it.date) }
                    _uiState.update { it.copy(eventsPerDay = map) }
                }
        }
    }

    // Generates full month grid including previous/next month days
    fun generateMonthDates(selectedDate: LocalDate): List<List<LocalDate?>> {
        val yearMonth = selectedDate.withDayOfMonth(1)
        val firstOfMonth = yearMonth
//        val lastOfMonth = selectedDate.withDayOfMonth(selectedDate.lengthOfMonth())

        val startDayOfWeek = firstOfMonth.dayOfWeek.value % 7 // Sunday = 0
        val totalDays = selectedDate.lengthOfMonth()

        val days = mutableListOf<LocalDate?>()

        // Add previous month's trailing days
        val prevMonth = selectedDate.minusMonths(1)
        val prevMonthDays = prevMonth.lengthOfMonth()
        for (i in startDayOfWeek - 1 downTo 0) {
            days.add(prevMonth.withDayOfMonth(prevMonthDays - i))
        }

        // Add current month days
        for (i in 1..totalDays) {
            days.add(selectedDate.withDayOfMonth(i))
        }

        // Add next month's leading days to complete the last week
        while (days.size % 7 != 0) {
            val nextMonth = selectedDate.plusMonths(1)
            val day = days.size % 7 + 1
            days.add(nextMonth.withDayOfMonth(day))
        }

        // Split into weeks
        return days.chunked(7)
    }

    fun onDaySelected(date: LocalDate) {
        _uiState.update {
            it.copy(
                selectedDate = date,
                dateMatrix = generateMonthDates(date)
            )
        }
    }

    fun toggleExpand() {
        _uiState.update { it.copy(isExpanded = !it.isExpanded) }
    }
}
