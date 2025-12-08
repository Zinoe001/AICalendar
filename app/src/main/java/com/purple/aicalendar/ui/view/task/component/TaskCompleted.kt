package com.purple.aicalendar.ui.view.task.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.purple.aicalendar.R
import com.purple.aicalendar.ui.components.AppButton
import com.purple.aicalendar.ui.components.AppText
import com.purple.aicalendar.ui.components.Gap
import com.purple.aicalendar.ui.theme.DarkGray
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.LightGray
import com.purple.aicalendar.ui.theme.LightGreen
import com.purple.aicalendar.ui.theme.MediumGray
import com.purple.aicalendar.ui.theme.PrimaryColor

@Composable
fun TaskCompleted(
    modifier: Modifier = Modifier,
    numKept: Int,
    numDiscarded: Int,
    onTap: () -> Unit,
){
        Column(
            modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier
                    .size(Dimens.dp(100))
                    .background(LightGreen, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = "",
                    modifier = Modifier
                        .size(Dimens.dp30) // set a fixed size
                )
            }
            Gap.H(Dimens.dp20)
            AppText(
                title = "All Set!",
                fontSize = Dimens.sp(24F),
                fontWeight = FontWeight.Bold
            )
            Gap.H(Dimens.dp20)
            AppText(
                title = "You've reviewed all 10 suggestions",
                color = MediumGray,
                fontSize = Dimens.sp(13),
                fontWeight = FontWeight.W500
            )
            Gap.H(Dimens.dp20)
            Row {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppText(
                        title = "$numKept",
                        fontSize = Dimens.sp(28F),
                        fontWeight = FontWeight.Bold,
                        color = PrimaryColor
                    )
                    Gap.H(Dimens.dp8)
                    AppText(
                        title = "Kept",
                        fontSize = Dimens.sp(12),
                        fontWeight = FontWeight.W400,
                        maxLines = 2,
                        color = MediumGray,
                    )
                }
                Gap.W(Dimens.dp20)
                VerticalDivider(
                    color = DarkGray,
                    thickness = Dimens.dp(1),
                    modifier = modifier.height(Dimens.dp(60))
                )
                Gap.W(Dimens.dp8)
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppText(
                        title = "$numDiscarded",
                        fontSize = Dimens.sp(28F),
                        fontWeight = FontWeight.Bold,
                        color = LightGray
                    )
                    Gap.H(Dimens.dp8)
                    AppText(
                        title = "Discarded",
                        fontSize = Dimens.sp(12),
                        fontWeight = FontWeight.W400,
                        maxLines = 2,
                        color = MediumGray,
                    )
                }
            }
            Gap.H(Dimens.dp20)
            AppButton.Primary(text = "View My Calender", onTap = { onTap() })
        }
}