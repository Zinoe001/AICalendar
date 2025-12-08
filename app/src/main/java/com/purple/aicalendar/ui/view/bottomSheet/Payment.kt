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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.purple.aicalendar.ui.components.AppTextField
import com.purple.aicalendar.ui.components.Gap
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.LightGray
import com.purple.aicalendar.ui.theme.PrimaryColor
import com.purple.aicalendar.ui.theme.White

@Composable
fun Payment(
    modifier: Modifier = Modifier,
    onDismiss: ()-> Unit,
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
                            title = "Enter Your PIN",
                            fontSize = Dimens.sp(20F),
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                        Gap.H(Dimens.dp4)
                        AppText(
                            title = "Authorize this transaction",
                            color = White,
                            fontSize = Dimens.sp(12F),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
        Gap.H(Dimens.dp24)
        Box(
            modifier
                .padding(horizontal = Dimens.dp16)
                .background(color = Color(0xFFECECEC), RoundedCornerShape(Dimens.dp6)),
            contentAlignment = Alignment.Center
        ){
            Row(modifier
                .padding(Dimens.dp16),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AppText(
                    modifier = Modifier.weight(1F),
                    title = "Amount",
                    fontSize = Dimens.sp(14F),
                    fontWeight = FontWeight.Normal
                )
                AppText(
                    title = "₦${event.amount}",
                    color = PrimaryColor,
                    fontSize = Dimens.sp(20F),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Gap.H(Dimens.dp20)
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            AppText(
                title = "Transaction PIN",
                color = LightGray,
                fontSize = Dimens.sp(12F),
                fontWeight = FontWeight.Medium
            )
        }
        Gap.H(Dimens.dp20)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            AppTextField.Pin(pinLength = 4, onPinEntered = {})
        }
        Gap.H(Dimens.dp24)
        AppButton.Primary(modifier
            .padding(horizontal = Dimens.dp16),
            text="Confirm Payment",
            onTap = {}
        )
        Gap.H(Dimens.dp10)
    }
}