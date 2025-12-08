package com.purple.aicalendar.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.purple.aicalendar.ui.theme.Dimens

/**
 * A utility object for creating horizontal and vertical spacing in Compose layouts.
 * It provides simple, reusable Composables for adding gaps of a specified dimension.
 *
 * Example usage:
 * ```
 * Column {
 *     Text("Top Item")
 *     Gap.H(Dimens.dp20) // Adds a vertical gap of 20dp
 *     Text("Bottom Item")
 * }
 *
 * Row {
 *     Text("Left Item")
 *     Gap.W() // Adds a horizontal gap of the default size (10dp)
 *     Text("Right Item")
 * }
 * ```
 */
object Gap {
    @Composable
    fun H(dimen : Dp = Dimens.dp10){
        Spacer(modifier = Modifier.heightIn(dimen))
    }

    @Composable
    fun W(dimen : Dp = Dimens.dp10){
        Spacer(modifier = Modifier.widthIn(dimen))
    }
}