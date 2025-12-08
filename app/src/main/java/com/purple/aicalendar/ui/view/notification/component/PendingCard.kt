package com.purple.aicalendar.ui.view.notification.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.purple.aicalendar.R
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.ui.components.AppText
import com.purple.aicalendar.ui.components.Gap
import com.purple.aicalendar.ui.theme.DarkYellow
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.LightGray
import com.purple.aicalendar.ui.theme.LightYellow
import com.purple.aicalendar.ui.theme.MediumYellow

@Composable
fun PendingCard(
    modifier: Modifier = Modifier,
    event: Event
){
    Box(
        modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.dp6)
            .background(color = LightYellow,shape = RoundedCornerShape(Dimens.dp12))
//            .clip(shape = RoundedCornerShape(Dimens.dp12))
            .border(width = Dimens.dp(1), color = MediumYellow,shape = RoundedCornerShape(Dimens.dp12))
    ){
        Column(
            modifier
                .padding(all = Dimens.dp8)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ){
            Row (
                verticalAlignment = Alignment.Top
            ){
                Box(
                    modifier
                        .size(Dimens.dp(28))
                        .background(MediumYellow, CircleShape)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_calender),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(DarkYellow),
                        modifier = Modifier
                            .size(Dimens.dp14) // set a fixed size
                    )
                }
                Gap.W(Dimens.dp(10))
                Column(modifier.weight(1F)) {
                    AppText(
                        title = "Netflix Subscription",
                        fontSize = Dimens.sp(14F),
                        fontWeight = FontWeight.Medium)
                    Gap.H(Dimens.dp4)
                    AppText(
                        title =event.obligee?: "Netflix Inc",
                        fontSize = Dimens.sp(10F),
                        fontWeight = FontWeight.Normal,
                        color = LightGray
                    )
                    Gap.H(Dimens.dp10)
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_time),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(DarkYellow),
                            modifier = Modifier
                                .size(Dimens.dp16) // set a fixed size
                        )
                        Gap.W(Dimens.dp6)
                        AppText(
                            title = "Due in 24 hours",
                            fontSize = Dimens.sp(10F),
                            fontWeight = FontWeight.SemiBold,
                            color = DarkYellow
                        )
                    }
                }
                AppText(
                    modifier,
                    title = "₦${event.amount}",
                    fontSize = Dimens.sp(12F),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}