package com.purple.aicalendar.ui.view.bottomSheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.purple.aicalendar.R
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.ui.components.AppButton
import com.purple.aicalendar.ui.components.AppText
import com.purple.aicalendar.ui.components.Gap
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.LightGray
import com.purple.aicalendar.ui.theme.PrimaryColor
import com.purple.aicalendar.ui.theme.White

@Composable
fun ConfirmPayment(
    modifier: Modifier = Modifier,
    onDismiss: ()-> Unit,
    onProceed: (Event)-> Unit,
    event: Event,
){
    Column(
        modifier
            .fillMaxWidth()
            .height(Dimens.dp(400))
            .background(White),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryColor)
                .padding(
                    start = Dimens.dp20,
                    top = Dimens.dp8,
                    end = Dimens.dp8,
                    bottom = Dimens.dp20
                )

        ) {
            Column{
                Row(
                    modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier
                            .size(Dimens.dp(32))
                            .background(Color(0xFFD67A7A), CircleShape)
                            .clip(CircleShape)
                            .clickable(onClick = onDismiss),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(White),
                            modifier = Modifier
                                .size(Dimens.dp16) // set a fixed size


                        )
                    }
                }

                Row(
                    modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier
                            .size(Dimens.dp(32))
                            .background(Color(0xFFD67A7A), CircleShape)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_lock),
                            contentDescription = "",
                            modifier = Modifier
                                .size(Dimens.dp16) // set a fixed size

                        )
                    }
                    Gap.W(Dimens.dp(10))
                    Column(modifier.weight(1F)) {
                        AppText(
                            title = "Confirm Payment",
                            fontSize = Dimens.sp(20F),
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                        Gap.H(Dimens.dp4)
                        AppText(
                            title = "Review payment details",
                            color = White,
                            fontSize = Dimens.sp(12F),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
        Gap.H(Dimens.dp(50))
        Row(
            modifier.padding(horizontal = Dimens.dp20),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppText(
                modifier = Modifier.weight(1F),
                title = "To",
                color = LightGray,
                fontSize = Dimens.sp(12F),
                fontWeight = FontWeight.Medium
            )
            AppText(
                title = event.obligee?: "Netflix Inc",
                fontSize = Dimens.sp(14F),
                fontWeight = FontWeight.Medium
            )
        }
        Gap.H(Dimens.dp10)
        Row(
            modifier.padding(horizontal = Dimens.dp20),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppText(
                modifier = Modifier.weight(1F),
                title = "Account",
                color = LightGray,
                fontSize = Dimens.sp(12F),
                fontWeight = FontWeight.Medium
            )
            AppText(
                title = event.accountNumber?: "0123456789",
                fontSize = Dimens.sp(12F),
                fontWeight = FontWeight.Medium
            )
        }
        Gap.H(Dimens.dp10)
        HorizontalDivider(
            modifier.padding(horizontal = Dimens.dp20),
            color = LightGray,
            thickness = Dimens.dp(1)
        )
        Gap.H(Dimens.dp10)
        Row(
            modifier.padding(horizontal = Dimens.dp20),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppText(
                modifier = Modifier.weight(1F),
                title = "Amount",
                color = LightGray,
                fontSize = Dimens.sp(12F),
                fontWeight = FontWeight.Medium
            )
            AppText(
                title = "₦${event.amount}",
                color = PrimaryColor,
                fontSize = Dimens.sp(14F),
                fontWeight = FontWeight.Medium
            )
        }
        Gap.H(Dimens.dp20)
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){

            AppText(
                title = "Do you want to make this payment now?",
                color = LightGray,
                fontSize = Dimens.sp(12F),
                fontWeight = FontWeight.Medium
            )
        }
        Gap.H(Dimens.dp30)
        Row(
            modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.dp20),

            ){
            AppButton.Outlined(
                modifier.weight(1F),
                text="Cancel",
                onTap = onDismiss
            )
            Gap.W(Dimens.dp8)
            AppButton.Primary(
                modifier.weight(1F),
                text="Yes, Proceed",
                onTap = { onProceed(event)}
            )
        }
        Gap.H(Dimens.dp10)
    }
}