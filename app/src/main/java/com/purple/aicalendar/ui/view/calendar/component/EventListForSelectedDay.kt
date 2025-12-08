package com.purple.aicalendar.ui.view.calendar.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.ui.components.AppText
import com.purple.aicalendar.ui.components.Gap
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.MediumGray
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun EventListForSelectedDay(
    allEvents: List<Event>,
    selectedDate: LocalDate,
    onPayNow: (Event) -> Unit,
    onEditClick: (Event) -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    // Filter events for only the selected day
    val eventsForDay = remember(allEvents, selectedDate) {
        allEvents.filter { event ->
            LocalDate.parse(event.date, formatter) == selectedDate
        }
    }

    // If no events for today → Show message
    if (eventsForDay.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Gap.H(Dimens.dp(50))
                AppText(
                    title = "No event today",
                    fontSize = Dimens.sp(16f),
                    color = MediumGray,
                    fontWeight = FontWeight.W500
                )
            }

        }
        return
    }

    // Show cards for selected-date events
    Column(modifier = Modifier.fillMaxWidth()) {

        eventsForDay.forEach { event ->
            val eventDate = LocalDate.parse(event.date, formatter)
            val today = LocalDate.now()

            when {
                eventDate.isBefore(today) -> {
                    NotificationCard.Inactive(
                        event = event,
                        onPayNow = onPayNow,
                        onEditClick = onEditClick
                    )
                }

                eventDate.isEqual(today) -> {
                    NotificationCard.Active(
                        event = event,
                        onPayNow = onPayNow,
                        onEditClick = onEditClick
                    )
                }

                eventDate.isAfter(today) -> {
                    NotificationCard.Upcoming(
                        event = event,
                        onPayNow = onPayNow,
                        onEditClick = onEditClick
                    )
                }
            }
        }
    }
}
