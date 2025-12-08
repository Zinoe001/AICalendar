package com.purple.aicalendar.ui.view.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.purple.aicalendar.R
import com.purple.aicalendar.ui.components.AppButton
import com.purple.aicalendar.ui.components.AppHeader
import com.purple.aicalendar.ui.components.AppText
import com.purple.aicalendar.ui.components.Gap
import com.purple.aicalendar.ui.viewmodel.SharedViewModel
import com.purple.aicalendar.ui.navigations.AICalendarScreens
import com.purple.aicalendar.ui.theme.DarkGray
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.PrimaryColor
import com.purple.aicalendar.ui.theme.SecondaryColor
import com.purple.aicalendar.ui.theme.White
import com.purple.aicalendar.ui.view.onboarding.component.OnboardingCard

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    vm: SharedViewModel = hiltViewModel()
    ) {
    val todayEvents by vm.todayEvents.collectAsState()
    val upcomingEvents by vm.upcomingEvents.collectAsState()
    LaunchedEffect(key1 = Unit) {
        vm.getTodayEvents()
        vm.getUpcomingEvents()
    }
    Column(
        modifier
            .fillMaxSize()
            .background(White)
            .padding(Dimens.dp20)
    ) {
        AppHeader(
            navController = navController,
        )
        Gap.H(Dimens.dp30)
        Box(
            modifier = Modifier.background(color = SecondaryColor, shape = RoundedCornerShape(Dimens.dp30))
        ) {
            Row(
                modifier .padding(horizontal = Dimens.dp16, vertical = Dimens.dp8),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = "",
                    modifier
                        .size(Dimens.dp20) // set a fixed size
                )
                Gap.W(Dimens.dp8)
                AppText(
                    title = "New Feature",
                    color = PrimaryColor,
                    fontSize = Dimens.sp(12),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Gap.H(Dimens.dp26)
        AppText(
            title = "Your Proactive",
            fontSize = Dimens.sp(32),
            fontWeight = FontWeight.Bold)
        Gap.H(Dimens.dp4)
        AppText(
            title = "Financial Assistant",
            color = PrimaryColor,
            fontSize = Dimens.sp(32),
            fontWeight = FontWeight.Bold)
        Gap.H(Dimens.dp(24))
        AppText(
            title = "ALAT now analyzes your past habits \n" +
                    "to predict upcoming bills, subscriptions, \n" +
                    "and savings. Review your month in seconds.",
            color = DarkGray,
            maxLines = 3,
            fontSize = Dimens.sp(13),
            fontWeight = FontWeight.W500)
        Gap.H(Dimens.dp(81))
        OnboardingCard(
            icon = R.drawable.ic_calender,
            title = "Smart Predictions",
            description = "We identify up to 10 key financial tasks for you.")
        Gap.H(Dimens.dp(33))
        OnboardingCard(
            icon = R.drawable.ic_trend,
            title = "Keep or Discard",
            description = "Review each suggestion and build your calendar.")
        Gap.H(Dimens.dp(33))
        OnboardingCard(
            icon = R.drawable.ic_notification,
            title = "24-Hour Reminders",
            description = "Get notified one day before each payment is due.")
        Gap.H(Dimens.dp(33))
        AppButton.Primary(
            text = "Start Prediction",
            modifier = Modifier.fillMaxWidth(),
            onTap = {
                navController.navigate(AICalendarScreens.TaskScreen) {
//                    popUpTo(AICalenderScreens.OnboardingScreen) { inclusive = true }
                }
            })
    }
}
