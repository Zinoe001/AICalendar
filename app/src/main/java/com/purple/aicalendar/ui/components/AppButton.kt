package com.purple.aicalendar.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.purple.aicalendar.ui.theme.Black
import com.purple.aicalendar.ui.theme.DarkGray
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.PrimaryColor

/**
 * A collection of standardized button composables for the application.
 * This object provides different styles of buttons, such as `Primary` and `Secondary`,
 * to ensure a consistent look and feel across the UI.
 */
object AppButton{

    @Composable
    fun Primary(
        modifier: Modifier = Modifier,
        text: String,
        onTap: () -> Unit,
        textColor: Color = Color.Companion.White,
        backgroundColor: Color = PrimaryColor
    ){
        Button(
            onClick = onTap,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp).height(Dimens.dp(55)),
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = Color.Companion.White
            ),
            shape = RoundedCornerShape(12.dp),
        ) {
            Text(text = text, fontSize = 16.sp, color = textColor)
        }
    }

    @Composable
    fun Secondary(
        modifier: Modifier = Modifier,
        text: String,
        onTap: () -> Unit,
        textColor: Color = DarkGray,
    ){
        Button(
            onClick = onTap,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp).height(Dimens.dp(55)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFECECEC),
                contentColor = textColor
            ),
            shape = RoundedCornerShape(12.dp),
        ) {
            Text(text = text, fontSize = 16.sp)
        }
    }

    @Composable
    fun Outlined(
        modifier: Modifier = Modifier,
        text: String,
        onTap: () -> Unit,
        textColor: Color = Black,
        backgroundColor: Color = Color.Transparent,
    ){
        Button(
            onClick = onTap,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp).height(Dimens.dp(55)),
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = textColor
            ),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(
                width = Dimens.dp(1),
                color = Color(0xFFBAB0B0)
            )
        ) {
            Text(text = text, fontSize = 16.sp)
        }
    }

}