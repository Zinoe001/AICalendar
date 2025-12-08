package com.purple.aicalendar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.PrimaryColor

/**
 * A composable that displays a full-screen loading indicator over the UI.
 *
 * This loader consists of a semi-transparent black overlay that covers the entire screen,
 * with a centered [CircularProgressIndicator]. It is conditionally displayed based on the [show] parameter.
 *
 * @param show If true, the loader is displayed. If false, nothing is rendered.
 * @param modifier The [Modifier] to be applied to the overlay container. Defaults to [Modifier].
 */
@Composable
fun AppLoader(
    show: Boolean,
    modifier: Modifier = Modifier
) {
    if (show) {
        // Overlay that lets the user see the underlying UI
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f)) ,// semi-transparent overlay
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = PrimaryColor,
                strokeWidth = Dimens.dp4
            )
        }
    }
}