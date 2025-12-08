package com.purple.aicalendar.ui.view.calendar.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.purple.aicalendar.core.utils.DateExtension
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.ui.components.AppText
import com.purple.aicalendar.ui.components.Gap
import com.purple.aicalendar.ui.theme.Black
import com.purple.aicalendar.ui.theme.DarkGray
import com.purple.aicalendar.ui.theme.DarkYellow
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.LightGray
import com.purple.aicalendar.ui.theme.LightGreen
import com.purple.aicalendar.ui.theme.LightYellow
import com.purple.aicalendar.ui.theme.MediumGray
import com.purple.aicalendar.ui.theme.MediumGreen
import com.purple.aicalendar.ui.theme.MediumYellow
import com.purple.aicalendar.ui.theme.PrimaryColor
import com.purple.aicalendar.ui.theme.SecondaryColor
import com.purple.aicalendar.ui.theme.White

object NotificationCard{
    @Composable
    fun Active(
        modifier: Modifier = Modifier,
        event: Event,
        onPayNow: (Event) -> Unit,
        onEditClick:(Event) -> Unit
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.dp(5))
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(Dimens.dp4)
                    .clip(RoundedCornerShape(topStart = Dimens.dp(40), bottomStart = Dimens.dp(40)))
                    .background(PrimaryColor)
            )
            Gap.W(Dimens.dp(10))
            Box(
                modifier = Modifier
                    .size(Dimens.dp(50))
                    .background(SecondaryColor, RoundedCornerShape(Dimens.dp(12))),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_calender),
                    contentDescription = "",
                    modifier = Modifier
                        .size(Dimens.dp(20)) // set a fixed size
                )
            }
            Gap.W(Dimens.dp(16))
            Column (
                modifier = Modifier.weight(1f)){
                Gap.H(Dimens.dp4)
                AppText(
                    title = event.title,
                    fontSize = Dimens.sp(16F),
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                Gap.H(Dimens.dp2)
                AppText(
                    title = DateExtension.formatUtcDateWithDay( event.date),
                    fontSize = Dimens.sp(12),
                    fontWeight = FontWeight.W400,
                    color = MediumGray,
                )
                Gap.H(Dimens.dp4)
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    AppText(
                        title = "Due today",
                        fontSize = Dimens.sp(12),
                        fontWeight = FontWeight.W400,
                        color = PrimaryColor,
                    )
                    Spacer(modifier = Modifier.weight(1F))
                    Box(
                        modifier
                            .size(Dimens.dp(35))
                            .padding(Dimens.dp(6))
                            .clickable(onClick ={ onEditClick(event)})
                            .background(color = Color(0xFFECECEC), shape = RoundedCornerShape(Dimens.dp(2))),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Black),
                            modifier = Modifier
                                .size(Dimens.dp(12)) // set a fixed size
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                val formattedAmount = try {
                    val amountValue = event.amount.toDoubleOrNull() ?: 0.0
                    val inThousands = amountValue / 1000
                    "₦${inThousands}k"
                } catch (e: Exception) {
                    "₦0K"
                }
                AppText(
                    title = formattedAmount,
                    fontSize = Dimens.sp(10F),
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                Gap.H(Dimens.dp4)
                Box(
                    modifier
                        .background(color = LightGreen, shape = RoundedCornerShape(Dimens.dp(6))),
                    contentAlignment = Alignment.Center
                ) {
                    AppText(
                        modifier = Modifier
                            .padding(horizontal = Dimens.dp(8), vertical = Dimens.dp(4)),
                        title = "Active",
                        fontSize = Dimens.sp(14F),
                        fontWeight = FontWeight.W500,
                        color = MediumGreen
                    )
                }
                Gap.H(Dimens.dp2)
                Box(
                    modifier
                        .background(color = PrimaryColor, shape = RoundedCornerShape(Dimens.dp(6)))
                        .clickable(onClick ={ onPayNow(event)}),
                    contentAlignment = Alignment.Center
                ) {
                    AppText(
                        modifier = Modifier
                            .padding(horizontal = Dimens.dp(8), vertical = Dimens.dp(4)),
                        title = "Pay Now",
                        fontSize = Dimens.sp(14F),
                        fontWeight = FontWeight.W500,
                        color = White
                    )
                }
            }
        }
    }


    @Composable
    fun Inactive(
        modifier: Modifier = Modifier,
        event: Event,
        onPayNow: (Event) -> Unit,
        onEditClick:(Event) -> Unit
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.dp(5))
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(Dimens.dp4)
                    .clip(RoundedCornerShape(topStart = Dimens.dp(40), bottomStart = Dimens.dp(40)))
                    .background(DarkGray)
            )
            Gap.W(Dimens.dp(10))
            Box(
                modifier = Modifier
                    .size(Dimens.dp(50))
                    .background(LightGray, RoundedCornerShape(Dimens.dp(12))),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_calender),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(DarkGray),
                    modifier = Modifier
                        .size(Dimens.dp(20)) // set a fixed size
                )
            }
            Gap.W(Dimens.dp(16))
            Column (
                modifier = Modifier.weight(1f)){
                Gap.H(Dimens.dp4)
                AppText(
                    title = event.title,
                    fontSize = Dimens.sp(16F),
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                Gap.H(Dimens.dp2)
                AppText(
                    title = DateExtension.formatUtcDateWithDay( event.date),
                    fontSize = Dimens.sp(12),
                    fontWeight = FontWeight.W400,
                    color = MediumGray,
                )
                Gap.H(Dimens.dp4)
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Spacer(modifier = Modifier.weight(1F))
                    Box(
                        modifier
                            .size(Dimens.dp(35))
                            .padding(Dimens.dp(6))
                            .clickable(onClick ={ onEditClick(event)})
                            .background(color = Color(0xFFECECEC), shape = RoundedCornerShape(Dimens.dp(2))),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Black),
                            modifier = Modifier
                                .size(Dimens.dp(12)) // set a fixed size
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                val formattedAmount = try {
                    val amountValue = event.amount.toDoubleOrNull() ?: 0.0
                    val inThousands = amountValue / 1000
                    "₦${inThousands}k"
                } catch (e: Exception) {
                    "₦0K"
                }
                AppText(
                    title = formattedAmount,
                    fontSize = Dimens.sp(10F),
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                Gap.H(Dimens.dp4)
                Box(
                    modifier
                        .background(color = LightGray, shape = RoundedCornerShape(Dimens.dp(6))),
                    contentAlignment = Alignment.Center
                ) {
                    AppText(
                        modifier = Modifier
                            .padding(horizontal = Dimens.dp(8), vertical = Dimens.dp(4)),
                        title = "Inactive",
                        fontSize = Dimens.sp(14F),
                        fontWeight = FontWeight.W500,
                        color = MediumGray
                    )
                }
                Gap.H(Dimens.dp2)
                Box(
                    modifier
                        .background(color = PrimaryColor, shape = RoundedCornerShape(Dimens.dp(6)))
                        .clickable(onClick ={ onPayNow(event)}),
                    contentAlignment = Alignment.Center
                ) {
                    AppText(
                        modifier = Modifier
                            .padding(horizontal = Dimens.dp(8), vertical = Dimens.dp(4)),
                        title = "Pay Now",
                        fontSize = Dimens.sp(14F),
                        fontWeight = FontWeight.W500,
                        color = White
                    )
                }
            }
        }
    }


    @Composable
    fun Upcoming(
        modifier: Modifier = Modifier,
        event: Event,
        onPayNow: (Event) -> Unit,
        onEditClick:(Event) -> Unit
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.dp(5))
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(Dimens.dp4)
                    .clip(RoundedCornerShape(topStart = Dimens.dp(40), bottomStart = Dimens.dp(40)))
                    .background(DarkYellow)
            )
            Gap.W(Dimens.dp(10))
            Box(
                modifier = Modifier
                    .size(Dimens.dp(50))
                    .background(MediumYellow, RoundedCornerShape(Dimens.dp(12))),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_calender),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(DarkYellow),
                    modifier = Modifier
                        .size(Dimens.dp(20)) // set a fixed size
                )
            }
            Gap.W(Dimens.dp(16))
            Column (
                modifier = Modifier.weight(1f)){
                Gap.H(Dimens.dp4)
                AppText(
                    title = event.title,
                    fontSize = Dimens.sp(16F),
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                Gap.H(Dimens.dp2)
                AppText(
                    title = DateExtension.formatUtcDateWithDay( event.date),
                    fontSize = Dimens.sp(12),
                    fontWeight = FontWeight.W400,
                    color = MediumGray,
                )
                Gap.H(Dimens.dp4)
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    AppText(
                        title = "Upcoming",
                        fontSize = Dimens.sp(12),
                        fontWeight = FontWeight.W400,
                        color = DarkYellow,
                    )
                    Spacer(modifier = Modifier.weight(1F))
                    Box(
                        modifier
                            .size(Dimens.dp(35))
                            .padding(Dimens.dp(6))
                            .clickable(onClick ={ onEditClick(event)})
                            .background(color = Color(0xFFECECEC), shape = RoundedCornerShape(Dimens.dp(2))),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Black),
                            modifier = Modifier
                                .size(Dimens.dp(12)) // set a fixed size
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                val formattedAmount = try {
                    val amountValue = event.amount.toDoubleOrNull() ?: 0.0
                    val inThousands = amountValue / 1000
                    "₦${inThousands}k"
                } catch (e: Exception) {
                    "₦0K"
                }
                AppText(
                    title = formattedAmount,
                    fontSize = Dimens.sp(10F),
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                Gap.H(Dimens.dp4)
                Box(
                    modifier
                        .background(color = LightYellow, shape = RoundedCornerShape(Dimens.dp(6))),
                    contentAlignment = Alignment.Center
                ) {
                    AppText(
                        modifier = Modifier
                            .padding(horizontal = Dimens.dp(8), vertical = Dimens.dp(4)),
                        title = "Upcoming",
                        fontSize = Dimens.sp(14F),
                        fontWeight = FontWeight.W500,
                        color = MediumYellow
                    )
                }
                Gap.H(Dimens.dp2)
                Box(
                    modifier
                        .background(color = PrimaryColor, shape = RoundedCornerShape(Dimens.dp(6)))
                        .clickable(onClick ={ onPayNow(event)}),
                    contentAlignment = Alignment.Center
                ) {
                    AppText(
                        modifier = Modifier
                            .padding(horizontal = Dimens.dp(8), vertical = Dimens.dp(4)),
                        title = "Pay Now",
                        fontSize = Dimens.sp(14F),
                        fontWeight = FontWeight.W500,
                        color = White
                    )
                }
            }
        }
    }
}