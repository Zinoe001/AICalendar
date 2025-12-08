package com.purple.aicalendar.ui.view.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.purple.aicalendar.domain.models.CalendarDay
import com.purple.aicalendar.ui.components.AppText
import com.purple.aicalendar.ui.theme.PrimaryColor
import java.time.LocalDate

@Composable
fun DayCalendarCell(
    day: CalendarDay,
    selectedDate: LocalDate,   // ← ADD THIS
    modifier: Modifier = Modifier,
    onSelect: () -> Unit
) {
    val isSelected = day.date == selectedDate

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onSelect() }
            .background(
                if (isSelected) PrimaryColor else Color.Transparent,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        AppText(
            title = day.date.dayOfMonth.toString(),
            color = if (isSelected) Color.White else Color.Black
        )
    }
}
