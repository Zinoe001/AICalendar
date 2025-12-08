package com.purple.aicalendar.ui.view.calendar.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.purple.aicalendar.domain.models.CalendarDay
import com.purple.aicalendar.ui.theme.Dimens
import kotlinx.coroutines.flow.filter
import java.time.LocalDate

@Composable
fun DaySlider(
    days: List<CalendarDay>,
    startIndex: Int,
    onDaySelected: (LocalDate) -> Unit
) {
    val listState = rememberLazyListState()

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val itemWidth = maxWidth / 7

        // Auto-scroll to today
        LaunchedEffect(Unit) {
            snapshotFlow { listState.layoutInfo.totalItemsCount }
                .filter { it > 0 }
                .collect {
                    if (startIndex != -1) {
                        val scrollPosition = startIndex - 3 // Center the start index
                        listState.animateScrollToItem(if (scrollPosition < 0) 0 else scrollPosition)
                    }
                }
        }

        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.dp16),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(days) { _, day ->
                DayItem(
                    day = day,
                    width = itemWidth,
                    onClick = {
                        onDaySelected(day.date)
                    }
                )
            }
        }
    }
}