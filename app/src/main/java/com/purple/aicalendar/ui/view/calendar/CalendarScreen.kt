package com.purple.aicalendar.ui.view.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.purple.aicalendar.R
import com.purple.aicalendar.core.utils.ScreenState
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.ui.components.AppHeader
import com.purple.aicalendar.ui.components.AppLoader
import com.purple.aicalendar.ui.components.AppText
import com.purple.aicalendar.ui.components.CustomBottomSheet
import com.purple.aicalendar.ui.components.CustomListView
import com.purple.aicalendar.ui.components.Gap
import com.purple.aicalendar.ui.navigations.AICalendarScreens
import com.purple.aicalendar.ui.theme.DarkGray
import com.purple.aicalendar.ui.theme.DarkRed
import com.purple.aicalendar.ui.theme.DarkYellow
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.LightRed
import com.purple.aicalendar.ui.theme.LightYellow
import com.purple.aicalendar.ui.theme.MediumGreen
import com.purple.aicalendar.ui.theme.MediumRed
import com.purple.aicalendar.ui.theme.MediumYellow
import com.purple.aicalendar.ui.theme.White
import com.purple.aicalendar.ui.view.bottomSheet.ConfirmPayment
import com.purple.aicalendar.ui.view.bottomSheet.EditTask
import com.purple.aicalendar.ui.view.bottomSheet.Payment
import com.purple.aicalendar.ui.view.calendar.component.EventListForSelectedDay
import com.purple.aicalendar.ui.view.calendar.component.ExpandableCalendar
import com.purple.aicalendar.ui.view.calendar.component.InfoCard
import com.purple.aicalendar.ui.view.calendar.component.NotificationCard
import com.purple.aicalendar.ui.viewmodel.SharedViewModel

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    vm : CalendarViewModel = hiltViewModel(),
    sharedVm : SharedViewModel = hiltViewModel(),
    ) {
    LaunchedEffect(Unit) {
        vm.getAllEvents()
    }
    val uiState by vm.uiState.collectAsState()
    val sharedVmState by sharedVm.uiState.collectAsState()
    val allEvents by vm.allEvents.collectAsState()
    val selectedDate = uiState.selectedDate
    val monthMatrix = uiState.dateMatrix
    var selectedEvent by remember { mutableStateOf<Event?>(null) }
    var showPaymentSheet by remember { mutableStateOf(false) }
    var showEditSheet by remember { mutableStateOf(false) }

    Box(modifier
        .fillMaxSize()){
    Column (
        modifier
            .fillMaxSize()
            .background(White)
            .padding(Dimens.dp20)
    ) {
        AppHeader(
            navController = navController,
        )
        Gap.H(Dimens.dp16)
        AppText(
            title = selectedDate.month.name.lowercase().replaceFirstChar { it.uppercase() } +
                    " ${selectedDate.year}",
            fontSize = Dimens.sp(24F),
            fontWeight = FontWeight.Bold)
        Gap.H(Dimens.dp4)
        AppText(
            title = "8 tasks scheduled",
            color = DarkGray,
            fontSize = Dimens.sp(13),
            fontWeight = FontWeight.Medium
        )
        Gap.H(Dimens.dp24)
        ExpandableCalendar(
            monthDays = monthMatrix,
            selectedDate = selectedDate,
            expanded = uiState.isExpanded,
            eventsPerDay = uiState.eventsPerDay,
            onToggleExpand = { vm.toggleExpand() },
            onDaySelected = { vm.onDaySelected(it) }
        )
        Gap.H(Dimens.dp12)
        Box(
            modifier
                .fillMaxWidth()
                .blur(Dimens.dp8)
                .height(Dimens.dp2)
                .background(Color(0xFFFAF9F9))
        )
        Column(
            modifier
                .verticalScroll(rememberScrollState())
        ) {
            Gap.H(Dimens.dp12)
            InfoCard(
                title = "Due Today - Action Required",
                subTitle = "3 payments due today. Tap to proceed with transaction",
                icons = R.drawable.ic_time,
                bgColor = LightRed,
                borderColor = MediumRed,
                iconOverlay = MediumRed,
                isCircle = true,
                iconColor = DarkRed,
                textColor = DarkRed,
            )
            InfoCard(
                title = "Upcoming in 24 Hours",
                subTitle = "3 payments due soon. We'll remind you!",
                icons = R.drawable.ic_time,
                bgColor = LightYellow,
                borderColor = MediumYellow,
                iconOverlay = MediumYellow,
                isCircle = true,
                iconColor = DarkYellow,
                textColor = Color(0xFF744710),
            )
            InfoCard(
                title = "Calendar Updated Successfully",
                subTitle = "${allEvents.size} out of 10 suggestions added. Reminders set for 24hrs before due dates.",
                icons = R.drawable.ic_check_square_2,
                bgColor = Color(0xFFEEFCE8),
                borderColor = MediumGreen,
                iconOverlay = Color(0xFFE2FFCF),
                isCircle = false,
                textColor = Color(0xFF1B551A),
            )
            Gap.H(Dimens.dp24)
            AppText(
                title = "Upcoming Payments",
                fontSize = Dimens.sp(14F),
                fontWeight = FontWeight.SemiBold
            )
            Gap.H(Dimens.dp24)
            EventListForSelectedDay(
                allEvents = allEvents,
                selectedDate = selectedDate,  // ← LocalDate user selected
                onPayNow = { event ->
                    selectedEvent = event
                    showPaymentSheet = false
                },
                onEditClick = { event ->
                    selectedEvent = event
                    showEditSheet = true
                }
            )
        }
    }
        if (selectedEvent != null) {
            when {
                // 1️⃣ Edit Sheet
                showEditSheet -> {
                    CustomBottomSheet(
                        show = true,
                        onDismiss = {
                            showEditSheet = false
                            selectedEvent = null
                            showPaymentSheet = false
                        }
                    ) {
                        EditTask(
                            event = selectedEvent!!,
                            onDismiss = {
                                showEditSheet = false
                                selectedEvent = null
                                showPaymentSheet = false
                            }
                        )
                    }
                }

                // 2️⃣ Confirm Payment Sheet
                !showPaymentSheet -> {
                    CustomBottomSheet(
                        show = true,
                        onDismiss = {
                            selectedEvent = null
                            showPaymentSheet = false
                        }
                    ) {
                        ConfirmPayment(
                            event = selectedEvent!!,
                            onDismiss = {
                                selectedEvent = null
                                showPaymentSheet = false
                            },
                            onProceed = {
                                showPaymentSheet = true // Switch to Payment Sheet
                            }
                        )
                    }
                }

                // 3️⃣ Payment Sheet
                else -> {
                    CustomBottomSheet(
                        show = true,
                        onDismiss = {
                            selectedEvent = null
                            showPaymentSheet = false
                        }
                    ) {
                        Payment(
                            event = selectedEvent!!,
                            onDismiss = {
                                selectedEvent = null
                                showPaymentSheet = false
                            }
                        )
                    }
                }
            }
        }
        // =======================
        // See-through loading overlay
        // =======================
        if (sharedVmState is ScreenState.LoadingState) {
            AppLoader(show = true)
        }

        when (sharedVmState) {
            is ScreenState.Success -> {
                LaunchedEffect(sharedVmState) {
                    vm.getAllEvents()
                    showEditSheet = false
                    selectedEvent = null
                }
            }

            else -> {}
        }

    }
}