package com.purple.aicalendar.ui.navigations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.purple.aicalendar.ui.view.calendar.CalendarScreen
import com.purple.aicalendar.ui.view.notification.NotificationScreen
import com.purple.aicalendar.ui.view.onboarding.OnboardingScreen
import com.purple.aicalendar.ui.view.task.TaskScreen
import com.purple.aicalendar.ui.viewmodel.SharedViewModel

/**
 * Composable function that sets up the navigation graph for the application.
 *
 * It uses a [NavHost] to define the different screens (destinations) and the navigation
 * logic between them. The starting destination is set to [AICalendarScreens.OnboardingScreen].
 *
 * @param navController The [NavHostController] that manages app navigation.
 */
@Composable
fun AICalendarNavigation(
    navController: NavHostController,
    vm: SharedViewModel = hiltViewModel(),

) {
    val allEvents = vm.allEvents.collectAsState().value
    val isLoaded = vm.isEventsLoaded.collectAsState().value

    LaunchedEffect(Unit) {
        vm.getAllEvents()
    }

    // ⛔ Don't render the NavHost until events are loaded
    if (!isLoaded) return

    NavHost(
        navController = navController,
        startDestination = if (allEvents.isNotEmpty())
            AICalendarScreens.CalendarScreen
        else
            AICalendarScreens.OnboardingScreen
    ) {

        composable<AICalendarScreens.OnboardingScreen> {
            OnboardingScreen(navController = navController)
        }

        composable<AICalendarScreens.TaskScreen> {
            TaskScreen(navController = navController)
        }

        composable<AICalendarScreens.CalendarScreen> {
            CalendarScreen(navController = navController)
        }

        composable<AICalendarScreens.NotificationScreen> {
            NotificationScreen(navController = navController)
        }
    }
}

