package com.purple.aicalendar.ui.view.calendar.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.purple.aicalendar.ui.components.AppText
import com.purple.aicalendar.ui.components.Gap
import com.purple.aicalendar.ui.theme.Black
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.LightYellow
import com.purple.aicalendar.ui.theme.White

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    bgColor: Color = White,
    borderColor: Color = LightYellow,
    iconColor: Color? = null,
    textColor: Color = Black,
    iconOverlay: Color = White,
    icons: Int,
    isCircle: Boolean = false,
    title: String,
    subTitle: String,
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(Dimens.dp(80))
            .padding(vertical = Dimens.dp6)
            .background(color = bgColor, shape = RoundedCornerShape(Dimens.dp12))
            .border(width = Dimens.dp(1), color = borderColor, shape = RoundedCornerShape(Dimens.dp12))
            .padding(all = Dimens.dp8),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier
                    .size(Dimens.dp(36))
                    .background(
                        color=iconOverlay,
                        shape = if (isCircle) CircleShape else RoundedCornerShape(Dimens.dp(6))),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = icons),
                    contentDescription = "",
                   colorFilter = if(iconColor == null) null else ColorFilter.tint(iconColor),
                    modifier = Modifier.size(Dimens.dp(18))
                )
            }
            Gap.W(Dimens.dp(10))
            Column(modifier.weight(1F)) {
                AppText(
                    title = title,
                    fontSize = Dimens.sp(14F),
                    fontWeight = FontWeight.Medium,
                    color = textColor
                )
                Gap.H(Dimens.dp4)
                AppText(
                    title = subTitle,
                    fontSize = Dimens.sp(10F),
                    fontWeight = FontWeight.Normal,
                    color = textColor
                )
            }
        }
    }
}
