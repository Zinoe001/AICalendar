package com.purple.aicalendar.ui.view.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.purple.aicalendar.core.utils.ScreenState
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.ui.components.*
import com.purple.aicalendar.ui.theme.*
import com.purple.aicalendar.ui.view.bottomSheet.EditTask
import com.purple.aicalendar.ui.view.task.component.TaskCompleted
import com.purple.aicalendar.ui.view.task.component.TaskSelection
import com.purple.aicalendar.ui.navigations.AICalendarScreens
import com.purple.aicalendar.ui.viewmodel.SharedViewModel

@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    vm: TaskViewModel = hiltViewModel(),
    sharedVm: SharedViewModel = hiltViewModel()
) {
    val uiState by vm.uiState.collectAsState()
    val uiSecondaryState by vm.uiSecondaryState.collectAsState()
    val sharedVmState by sharedVm.uiState.collectAsState()

    val events by vm.event.collectAsState()
    val allEvents by sharedVm.predictedEvents.collectAsState()
    val kept by vm.keptEvents.collectAsState()
    val discarded by vm.discardedEvents.collectAsState()
    val totalItems by vm.totalItems.collectAsState()

    var selectedEvent by remember { mutableStateOf<Event?>(null) }
    var isDiscarded by remember { mutableStateOf(false) }



    LaunchedEffect(allEvents) {
        vm.deleteAllTask = sharedVm::deleteEvents
        if (allEvents.isEmpty()) {
            vm.init()
        } else {
            vm.setAllEvents(allEvents)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {

        // -------------------------
        // Main Content
        // -------------------------
        Column(
            modifier
                .fillMaxSize()
                .background(White)
                .padding(Dimens.dp20)
                .then(if (selectedEvent != null) Modifier.blur(Dimens.dp(8)) else Modifier)
        ) {
            AppHeader(
                showBack = true,
                navController = navController
            )

            Gap.H(Dimens.dp16)

            when (uiState) {

                ScreenState.LoadingState -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = PrimaryColor,
                            strokeWidth = Dimens.dp(4)
                        )
                    }
                }

                is ScreenState.Success -> {
                    if (events.isEmpty()) {
                        TaskCompleted(
                            numKept = kept.size,
                            numDiscarded = discarded.size,
                            onTap = { vm.postEvent() }
                        )
                    } else {
                        Column {
                            AppText(
                                title = if (isDiscarded) "Discarded Tasks" else "Suggested Tasks",
                                fontSize = Dimens.sp(24f),
                                fontWeight = FontWeight.Bold
                            )

                            Gap.H(Dimens.dp4)

                            AppText(
                                title = "Review & customize predictions",
                                color = DarkGray,
                                fontSize = Dimens.sp(13),
                                fontWeight = FontWeight.W500
                            )

                            TaskSelection(
                                items = events,
                                discardItems = discarded,
                                onKeep = vm::keepEvent,
                                onDiscard = vm::discardEvent,
                                onRestore = vm::restoreEvent,
                                isDiscarded = isDiscarded,
                                kept = kept.size,
                                discarded = discarded.size,
                                totalItems = totalItems,
                                onDiscardTap = { isDiscarded = true },
                                onKeptTap = { isDiscarded = false },
                                onEditClick = { selectedEvent = it }
                            )
                        }
                    }
                }

                else -> Unit
            }
        }

        // -------------------------
        // Edit Sheet
        // -------------------------
        CustomBottomSheet(
            show = selectedEvent != null,
            onDismiss = { selectedEvent = null }
        ) {
            selectedEvent?.let { event ->
                EditTask(
                    event = event,
                    onDismiss = { selectedEvent = null }
                )
            }
        }

        // -------------------------
        // Unified Loading Overlay
        // -------------------------
        if (uiSecondaryState is ScreenState.LoadingState ||
            sharedVmState is ScreenState.LoadingState
        ) {
            AppLoader(show = true)
        }

        // -------------------------
        // Secondary Success Handling
        // -------------------------
        LaunchedEffect(uiSecondaryState) {
            if (uiSecondaryState is ScreenState.Success) {
                navController.navigate(AICalendarScreens.CalendarScreen) {
                    popUpTo(AICalendarScreens.OnboardingScreen) { inclusive = true }
                }
            }
        }

        LaunchedEffect(sharedVmState) {
            if (sharedVmState is ScreenState.Success) {
                sharedVm.getAllEvents(isPrediction = true)
                selectedEvent = null
            }
        }
    }
}
