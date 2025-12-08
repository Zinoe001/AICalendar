package com.purple.aicalendar.ui.navigations
import kotlinx.serialization.Serializable

/**
 * Represents the different screens/destinations in the AICalender application navigation graph.
 * This sealed class is used with the Type-Safe Navigation library to define all
 * possible routes. Each object represents a distinct screen.
 *
 * The `@Serializable` annotation allows the navigation library to automatically handle
 * serialization for argument passing and state restoration.
 *
 */
sealed class AICalendarScreens : Route{
    @Serializable
    data object OnboardingScreen: AICalendarScreens()

    @Serializable
    data object TaskScreen: AICalendarScreens()

    @Serializable
    data object CalendarScreen: AICalendarScreens()

    @Serializable
    data object NotificationScreen: AICalendarScreens()

}
