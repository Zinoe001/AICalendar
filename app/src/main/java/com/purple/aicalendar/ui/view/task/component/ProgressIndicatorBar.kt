package com.purple.aicalendar.ui.view.task.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.PrimaryColor

@Composable
fun ProgressIndicatorBar(
    progress: Float,      // 0f to 1f
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFFF0EFEF),
    progressColor: Color = PrimaryColor,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.dp6)
            .clip(RoundedCornerShape(Dimens.dp4))
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .clip(RoundedCornerShape(Dimens.dp4))
                .background(progressColor)
        )
    }
}