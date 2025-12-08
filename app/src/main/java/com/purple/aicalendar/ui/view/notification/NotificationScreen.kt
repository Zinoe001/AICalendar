package com.purple.aicalendar.ui.view.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.purple.aicalendar.R
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.ui.components.AppText
import com.purple.aicalendar.ui.components.CustomBottomSheet
import com.purple.aicalendar.ui.components.Gap
import com.purple.aicalendar.ui.viewmodel.SharedViewModel
import com.purple.aicalendar.ui.theme.DarkYellow
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.PrimaryColor
import com.purple.aicalendar.ui.theme.SecondaryColor
import com.purple.aicalendar.ui.theme.White
import com.purple.aicalendar.ui.view.bottomSheet.ConfirmPayment
import com.purple.aicalendar.ui.view.bottomSheet.Payment
import com.purple.aicalendar.ui.view.notification.component.DueList
import com.purple.aicalendar.ui.view.notification.component.PendingList

@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    vm: SharedViewModel = hiltViewModel()
) {
    val todayEvents by vm.todayEvents.collectAsState()
    val upcomingEvents by vm.upcomingEvents.collectAsState()
    val totalEvents = todayEvents.size + upcomingEvents.size
    var selectedEvent by remember { mutableStateOf<Event?>(null) }
    var showPaymentSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        vm.getTodayEvents()
        vm.getUpcomingEvents()
    }
    Box(modifier.fillMaxSize()) { // <-- Use Box for stacking
        // Main content
        Column(
            modifier
                .fillMaxSize()
                .background(White)
                .then(if (selectedEvent != null) Modifier.blur(Dimens.dp(8)) else Modifier)
        ) {
            Box(
                modifier
                    .background(PrimaryColor)
                    .fillMaxWidth()
                    .height(Dimens.dp(110))
                    .padding(
                        start = Dimens.dp24,
                        end = Dimens.dp24,
                        top = Dimens.dp(50),
                        bottom = Dimens.dp10
                    )

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier
                            .size(Dimens.dp(28))
                            .background(White, CircleShape)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_notification),
                            contentDescription = "",
                            modifier = Modifier
                                .size(Dimens.dp14) // set a fixed size
                        )
                    }
                    Gap.W(Dimens.dp(10))
                    Column(modifier.weight(1F)) {
                        AppText(
                            title = "Notifications",
                            fontSize = Dimens.sp(20F),
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                        Gap.H(Dimens.dp4)
                        AppText(
                            title = "$totalEvents pending reminders",
                            color = White,
                            fontSize = Dimens.sp(12F),
                            fontWeight = FontWeight.W600
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.ic_close_outlined),
                        contentDescription = "",
                        modifier = Modifier
                            .size(Dimens.dp28) // set a fixed size
                            .clickable(onClick = {
                                navController.popBackStack()
                            })
                    )
                }
            }
            Column(
                modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                Gap.H(Dimens.dp10)
                Row(
                    modifier.padding(horizontal = Dimens.dp24),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_info),
                        contentDescription = "",
                        modifier = Modifier
                            .size(Dimens.dp28) // set a fixed size
                            .clickable(onClick = {
                                navController.popBackStack()
                            })
                    )
                    Gap.W(Dimens.dp10)
                    AppText(
                        title = "Due Today",
                        fontSize = Dimens.sp(14F),
                        fontWeight = FontWeight.W500
                    )
                    Gap.W(Dimens.dp10)
                    Box(
                        modifier
                            .size(Dimens.dp(28))
                            .background(SecondaryColor, CircleShape)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        AppText(
                            title = todayEvents.size.toString(),
                            fontSize = Dimens.sp(12F),
                            color = PrimaryColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Gap.H(Dimens.dp10)
                if(todayEvents.isEmpty()){
                    Column(
                        modifier.fillMaxWidth().weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        AppText(
                            title = "No events today",
                            fontSize = Dimens.sp(14F),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }else{
                DueList(
                    request = todayEvents,
                    onTap = { event -> selectedEvent = event }
                )}
                Gap.H(Dimens.dp10)
                Row(
                    modifier.padding(horizontal = Dimens.dp24),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(DarkYellow),
                        modifier = Modifier
                            .size(Dimens.dp(28))
                    )
                    Gap.W(Dimens.dp10)
                    AppText(
                        title = "Upcoming (24hrs)",
                        fontSize = Dimens.sp(14F),
                        fontWeight = FontWeight.Medium
                    )
                }
                Gap.H(Dimens.dp10)
                if(upcomingEvents.isEmpty()){
                    Column(
                        modifier.fillMaxWidth().weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        AppText(
                            title = "No upcoming events",
                            fontSize = Dimens.sp(14F),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }else{
                    PendingList(request = upcomingEvents)
                }
                Gap.H(Dimens.dp10)
            }
        }

        // Bottom sheet logic
        if (selectedEvent != null) {
            if (!showPaymentSheet) {
                // Show ConfirmPayment first
                CustomBottomSheet(
                    show = true,
                    onDismiss = {
                        selectedEvent = null
                        showPaymentSheet = false // <-- reset here too
                    }
                ) {
                    selectedEvent?.let { event ->
                        ConfirmPayment(
                            event = event,
                            onDismiss = {
                                selectedEvent = null
                                showPaymentSheet = false // <-- reset here too
                            },
                            onProceed = {
                                showPaymentSheet = true // switch to Payment sheet
                            }
                        )
                    }
                }
            } else {
                // Show Payment sheet
                CustomBottomSheet(
                    show = true,
                    onDismiss = {
                        selectedEvent = null
                        showPaymentSheet = false // <-- reset here too
                    }
                ) {
                    selectedEvent?.let { event ->
                        Payment(
                            event = event,
                            onDismiss = {
                                selectedEvent = null
                                showPaymentSheet = false // <-- reset here too
                            }
                        )
                    }
                }
            }
        }
    }
}