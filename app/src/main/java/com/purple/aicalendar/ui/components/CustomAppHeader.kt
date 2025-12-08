package com.purple.aicalendar.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.purple.aicalendar.R
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.ui.navigations.AICalendarScreens
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.PrimaryColor
import com.purple.aicalendar.ui.theme.SecondaryColor
import com.purple.aicalendar.ui.theme.White
import com.purple.aicalendar.ui.viewmodel.SharedViewModel

@Composable
fun AppHeader(
    modifier: Modifier = Modifier,
    showBack: Boolean = false,
    navController: NavController,
    vm : SharedViewModel = hiltViewModel(),
) {
    val todayEvents by vm.todayEvents.collectAsState()
    val upcomingEvents by vm.upcomingEvents.collectAsState()
    LaunchedEffect(key1 = Unit) {
        vm.getTodayEvents()
        vm.getUpcomingEvents()
    }
    // Top Bar Area (Spacer for status bar)
    Gap.H(Dimens.dp(32))
    // Notification/Profile Icons (Mocked for layout)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(showBack) {
            Box(
                modifier
                    .size(Dimens.dp(40))
                    .graphicsLayer {
                        shadowElevation = Dimens.dp(2).toPx()
                        shape = CircleShape
                        clip = false
                    }
                    .background(White, CircleShape)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "",
                    modifier = Modifier
                        .size(Dimens.dp20) // set a fixed size
                        .clickable(onClick = {
                            navController.popBackStack()
                        })
                )
            }
        }
        Spacer(modifier = Modifier.weight(1F))
        Box(
            modifier
                .size(Dimens.dp(43))
        ) {
            Box(
                modifier
                    .size(Dimens.dp(40))
                    .graphicsLayer {
                        shadowElevation = Dimens.dp(2).toPx()
                        shape = CircleShape
                        clip = false
                    }
                    .background(White, CircleShape)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = "",
                    modifier = Modifier
                        .size(Dimens.dp20) // set a fixed size
                        .clickable(onClick = {
                            navController.navigate(AICalendarScreens.NotificationScreen)
                        })
                )
            }
            if (todayEvents.isNotEmpty()||upcomingEvents.isNotEmpty()) {
                // Badge count
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd) // use fresh Modifier
                        .size(Dimens.dp16)
                        .background(PrimaryColor, CircleShape),
                    contentAlignment = Alignment.Center
                ){
                    AppText(
                        title = "${todayEvents.size + upcomingEvents.size}",
                        color = Color.White,
                        fontSize = Dimens.sp(10),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Gap.W(Dimens.dp4)
        Box(
            modifier = Modifier
                .size(Dimens.dp(40))
                .background(SecondaryColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            AppText(title = "KB", color = PrimaryColor, fontWeight = FontWeight.Bold)
        }
    }
}