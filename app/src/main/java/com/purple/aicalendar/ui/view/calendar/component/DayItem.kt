package com.purple.aicalendar.ui.view.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.purple.aicalendar.domain.models.CalendarDay
import com.purple.aicalendar.ui.components.AppText
import com.purple.aicalendar.ui.theme.Black
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.PrimaryColor

@Composable
fun DayItem(
    day: CalendarDay,
    width: Dp,
    onClick: () -> Unit
) {
    val bgColor = when {
        day.isToday -> PrimaryColor   // Red like image
        day.isSelected -> Black
        else -> Color.Transparent
    }

    val textColor = if (day.isToday || day.isSelected) Color.White else Color.Black
    val shadow = if (!day.isSelected) Shadow(color = Color.Black.copy(alpha = 0.5f), offset = Offset(2f, 2f), blurRadius = 4f) else null

    Column(
        modifier = Modifier
            .width(width)
            .clip(RoundedCornerShape(Dimens.dp14))
            .clickable(onClick = onClick)
            .background(bgColor)
            .padding(vertical = Dimens.dp10, horizontal = Dimens.dp12),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppText(
            title = day.date.dayOfWeek.name.take(1), // S, M, T, W...
            color = textColor,
            fontSize = Dimens.sp(12),
            shadow = shadow
        )

        AppText(
            title = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = Dimens.sp(16),
            fontWeight = FontWeight.Bold,
            shadow = shadow
        )
    }
}
