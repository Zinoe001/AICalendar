package com.purple.aicalendar.ui.view.task.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.purple.aicalendar.R
import com.purple.aicalendar.core.utils.DateExtension
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.ui.components.AppButton
import com.purple.aicalendar.ui.components.AppText
import com.purple.aicalendar.ui.components.Gap
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.MediumGray
import com.purple.aicalendar.ui.theme.PrimaryColor
import com.purple.aicalendar.ui.theme.SecondaryColor

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    isDiscarded: Boolean = false,
    event: Event,
    onKeep: (Event) -> Unit= {},
    onDiscard: (Event) -> Unit= {},
    onRestore: (Event) -> Unit = {},
    onEditClick: (Event) -> Unit= {},
){
    Box(
        modifier
            .padding(Dimens.dp8)
            .graphicsLayer {
                shadowElevation = Dimens.dp(1).toPx()
                clip = false
            }
            ){
        Column(
            modifier
            .padding(Dimens.dp8),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Gap.H(Dimens.dp10)
            Row(
                modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AppText(
                    title = if(event.transactionType == "Bill") "UTILITY" else "FINANCE",
                    fontSize = Dimens.sp(12F),
                    fontWeight = FontWeight.Medium)
                AppText(
                    title = "${event.accuracy}% MATCH",
                    fontSize = Dimens.sp(12F),
                    fontWeight = FontWeight.Medium,
                    color = PrimaryColor
                    )
            }
            Gap.H(Dimens.dp10)
            Box(
                modifier
                    .size(Dimens.dp(80))
                    .background(SecondaryColor, RoundedCornerShape(Dimens.dp12)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_calender ),
                    contentDescription = "",
                    modifier = Modifier
                        .size(Dimens.dp28) // set a fixed size
                )
            }
            Gap.H(Dimens.dp20)
            AppText(
                title = event.title,
                fontSize = Dimens.sp(24F),
                fontWeight = FontWeight.Bold)
            Gap.H(Dimens.dp(8))
            AppText(
                title = "₦${event.amount}",
                fontSize = Dimens.sp(36F),
                fontWeight = FontWeight.Bold,
                color = PrimaryColor
            )
            Gap.H(Dimens.dp16)
            Box(
                modifier
                    .background(color = Color(0xFFECECEC), RoundedCornerShape(Dimens.dp6)),
                contentAlignment = Alignment.Center
            ){
                AppText(
                    modifier = Modifier
                        .padding(horizontal = Dimens.dp8, vertical = Dimens.dp10),
                    title = "Due: ${DateExtension.formatUtcDate(event.date)}",
                    fontSize = Dimens.sp(14F),
                    fontWeight = FontWeight.W500,
                    color = MediumGray
                )
            }
            Gap.H(Dimens.dp10)
            HorizontalDivider(color = Color(0xFFECECEC))
            Gap.H(Dimens.dp10)
            AppText(title ="Based on your activity:\"Payday pattern\"",
                fontSize = Dimens.sp(14F),
                fontWeight = FontWeight.W500,
                color = MediumGray)
            Gap.H(Dimens.dp20)
            if(isDiscarded){
                AppButton.Primary(text="Restore", onTap = {onRestore(event)})
            }else{
                Column {
                    Row(
                        modifier
                            .fillMaxWidth()
                            .clickable { onEditClick(event) },
                        horizontalArrangement = Arrangement.Center
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.ic_edit ),
                            contentDescription = "",
                            modifier = Modifier
                                .size(Dimens.dp16) // set a fixed size
                        )
                        Gap.W(Dimens.dp10)
                        AppText(
                            title ="Edit Details",
                            fontSize = Dimens.sp(14F),
                            fontWeight = FontWeight.W500,
                            color = PrimaryColor
                        )
                    }
                    Gap.H(Dimens.dp20)
                    Row{
                        AppButton.Secondary(modifier.weight(1F),text="Discard", onTap ={ onDiscard(event)})
                        Gap.W(Dimens.dp8)
                        AppButton.Primary(modifier.weight(1F),text="Keep", onTap = {onKeep(event)})
                    }
                }
            }

            Gap.H(Dimens.dp10)
        }
    }
}