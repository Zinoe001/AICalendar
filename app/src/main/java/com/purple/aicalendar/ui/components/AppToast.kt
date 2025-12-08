package com.purple.aicalendar.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.purple.aicalendar.R
import com.purple.aicalendar.ui.theme.Dimens
import kotlinx.coroutines.delay

/**
 * A composable that displays a toast-like message at the bottom of the screen.
 * The toast appears when [show] is true and automatically dismisses itself after 3 seconds.
 *
 * @param modifier The modifier to be applied to the component.
 * @param message The text message to display inside the toast.
 * @param show A boolean to control the visibility of the toast.
 * @param onDismiss A callback function that is invoked when the toast is dismissed, either automatically after a delay or by user action.
 */
@Composable
fun AppToast(
    modifier: Modifier,
    message: String,
    show: Boolean,
    onDismiss: () -> Unit
) {
    if (show) {
        // Use LaunchedEffect to auto-dismiss after some time
        LaunchedEffect(message) {
            delay(3000L) // 3 seconds
            onDismiss()
        }

        Box(
            modifier
                .fillMaxSize()
                .padding(bottom = Dimens.dp(100)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier
                    .background(Color(0xFFFF7961), RoundedCornerShape(Dimens.dp4))
                    .border(width = Dimens.dp(1), color = Color.Red, RoundedCornerShape(Dimens.dp4))
                    .padding(horizontal = Dimens.dp20, vertical = Dimens.dp12),

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AppText(
                        title = message,
                        fontSize = Dimens.sp(14F),
                        align = TextAlign.Center
                    )
                    Gap.W(Dimens.dp20)
                    Image(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .size(Dimens.dp18)
                    )
                }
            }
        }
    }
}