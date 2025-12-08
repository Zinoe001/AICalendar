package com.purple.aicalendar.ui.view.calendar.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.purple.aicalendar.ui.components.AppText
import com.purple.aicalendar.ui.components.Gap
import com.purple.aicalendar.ui.theme.Black
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.LightGray
import com.purple.aicalendar.ui.theme.PrimaryColor
import com.purple.aicalendar.R
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.ui.theme.SecondaryColor
import com.purple.aicalendar.ui.theme.White
import java.time.LocalDate

@Composable
fun ExpandableCalendar(
    monthDays: List<List<LocalDate?>>,
    eventsPerDay: Map<LocalDate, List<Event>>,
    selectedDate: LocalDate,
    expanded: Boolean,
    onToggleExpand: () -> Unit,
    onDaySelected: (LocalDate) -> Unit
) {
    val collapsedHeight = Dimens.dp(100)
    val rowHeight = Dimens.dp(50)
    val expandedHeight = rowHeight * monthDays.size + rowHeight

    // Animate based on expanded state only
    val animatedHeight by animateDpAsState(
        targetValue = if (expanded) expandedHeight else collapsedHeight,
        label = "calendarHeightAnimation"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(animatedHeight)
    ) {

        val daysOfWeek = listOf("S", "M", "T", "W", "T", "F", "S")

        if (expanded) {
            // ---------------- EXPANDED VIEW ----------------
            Row(modifier = Modifier.fillMaxWidth()) {
                daysOfWeek.forEach { day ->
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        AppText(
                            modifier = Modifier.blur(Dimens.dp(1)),
                            title = day,
                            fontSize = Dimens.sp(12),
                            color = Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Dimens.dp4))

            monthDays.forEach { week ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    week.forEach { day ->
                        if (day != null) {
                            val hasEvent = eventsPerDay.containsKey(day)
                            val isSelected = day == selectedDate
                            val isToday = day == LocalDate.now()
                            val isCurrentMonth = day.month == selectedDate.month

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .clickable { onDaySelected(day) }
                                    .padding(Dimens.dp2),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(if (isSelected) Dimens.dp(36) else Dimens.dp(32))
                                        .background(
                                            when {
                                                isSelected && !isToday ->LightGray
                                                isToday -> PrimaryColor
                                                else -> Color.Transparent
                                            },
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    AppText(
                                        modifier = if (!isSelected&&!isToday) Modifier.blur(Dimens.dp(1)) else Modifier,
                                        title = day.dayOfMonth.toString(),
                                        color = when {
                                            isSelected -> White
                                            isToday -> White
                                            hasEvent -> PrimaryColor
                                            isCurrentMonth -> Black
                                            else -> Color.Gray.copy(alpha = 0.5f)
                                        },
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    )
                                }
                            }
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.dp4))
            }

        } else {
            // ---------------- COLLAPSED VIEW ----------------
            val weekToShow = monthDays.firstOrNull { it.contains(selectedDate) }
                ?: monthDays.firstOrNull().orEmpty()

            Row(modifier = Modifier.fillMaxWidth()) {
                weekToShow.forEachIndexed { idx, day ->
                    val hasEvent = day?.let { eventsPerDay.containsKey(it) } == true
                    val isSelected = day == selectedDate
                    val isToday = day == LocalDate.now()
                    val isCurrentMonth = day?.month == selectedDate.month

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(enabled = day != null) { day?.let { onDaySelected(it) } }
                            .padding(Dimens.dp4),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val color = when {
                            isSelected -> Color.White
                            isToday -> Color.White
                            hasEvent -> PrimaryColor
                            isCurrentMonth -> Color.Black
                            else -> Color.Gray
                        }
                        val dayColor = when {
                            isSelected -> Color.White
                            isToday -> Color.White
                            isCurrentMonth -> Color.Black
                            else -> Color.Gray
                        }

                        Box(
                            modifier = Modifier
                                .size(Dimens.dp(64))
                                .background(
                                    when {
                                        isSelected && !isToday ->LightGray
                                        isToday -> PrimaryColor
                                        else -> Color.Transparent
                                    },
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                AppText(
                                    modifier = if (!isSelected&&!isToday) Modifier.blur(Dimens.dp(1)) else Modifier,
                                    title = daysOfWeek[idx],
                                    fontSize = Dimens.sp(10f),
                                    color = dayColor,
                                    fontWeight = FontWeight.Medium
                                )
                                Gap.H(Dimens.dp10)
                                AppText(
                                    modifier = if (!isSelected&&!isToday) Modifier.blur(Dimens.dp(1)) else Modifier,
                                    title = day?.dayOfMonth?.toString() ?: "",
                                    fontSize = Dimens.sp(14f),
                                    color = color,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
        // SMALL TOGGLE BUTTON (you can remove or customize this)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleExpand() }
                .padding(vertical = Dimens.dp2),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = if(expanded) R.drawable.ic_collapse else R.drawable.ic_expand),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Black),
                modifier = Modifier.size(Dimens.dp18)
            )
        }
    }
}
