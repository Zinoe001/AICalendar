package com.purple.aicalendar.core.utils

/**
 * Represents the various states a screen or UI component can be in,
 * typically used in conjunction with a ViewModel to drive the UI.
 * This is a generic implementation that can be extended or used directly.
 *
 * Common states include:
 * - `Loading`: When data is being fetched.
 * - `Success`: When data has been successfully loaded.
 * - `Error`: When an error has occurred during data fetching.
 * - `Empty`: When the data fetch was successful but returned no results.
 */
sealed class ScreenState {
    object Initial : ScreenState()
    data object LoadingState : ScreenState()
    data class Success(val message : String) : ScreenState()
    data class Error(val message : String) : ScreenState()
}