package com.purple.aicalendar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.LightGray
import com.purple.aicalendar.ui.theme.PrimaryColor
import com.purple.aicalendar.ui.theme.White

object AppTextField {
    @Composable
    fun Text(
        modifier: Modifier = Modifier,
        value: String,
        title: String? = null,
        description: String? = null,
        showError: Boolean = false,
        maxLines: Int = 1,
        height: Dp = Dimens.dp(55),
        onValueChange: (String) -> Unit,
    ) {
        val focusManager = LocalFocusManager.current
        var isFocused by remember { mutableStateOf(false) }

        Column {
            AppText(
                title = title ?: "Name",
                fontWeight = FontWeight.SemiBold,
                fontSize = Dimens.sp(12F),
                color = Color.Black
            )
            Gap.H(Dimens.dp4)
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(height)
//                    .clip(RoundedCornerShape(Dimens.dp4))
                    .border(
                        width = Dimens.dp(1),
                        color = if (isFocused) PrimaryColor else LightGray,
                        shape = RoundedCornerShape(Dimens.dp4)
                    )
                    .background(White)
                    .onFocusChanged { isFocused = it.isFocused }
                    .padding(horizontal = Dimens.dp12, vertical = if (isFocused) Dimens.dp10 else Dimens.dp6),// you control padding here
             contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    maxLines = maxLines,
                    singleLine = maxLines == 1,
                    textStyle = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = Dimens.sp(12F),
                        color = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default,
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    cursorBrush = androidx.compose.ui.graphics.SolidColor(PrimaryColor),
                    decorationBox = { innerTextField ->

                        if (value.isEmpty()) {
                            Text(
                                text = description ?: "John Doe",
                                color = Color.Gray,
                                fontSize = Dimens.sp(12F),
                                fontWeight = FontWeight.Medium,
                            )
                        }

                        innerTextField()
                    }
                )
            }
        }
    }


    @Composable
    fun Pin(
        pinLength: Int,
        onPinEntered: (String) -> Unit
    ) {
        val pin = remember { mutableStateOf("") }
        val focusRequester = remember { FocusRequester() }
        var isFocused by remember { mutableStateOf(false) }

        Box(modifier = Modifier
            .focusRequester(focusRequester)
            .clickable { focusRequester.requestFocus() })
        {
            BasicTextField(
                value = pin.value,
                onValueChange = {
                    if (it.length <= pinLength) {
                        pin.value = it
                        if (it.length == pinLength) {
                            onPinEntered(it)
                        }
                    }
                },
                modifier = Modifier.onFocusChanged { isFocused = it.isFocused },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                decorationBox = {
                    Row(horizontalArrangement = Arrangement.spacedBy(Dimens.dp8)) {
                        repeat(pinLength) { index ->
                            val char = pin.value.getOrNull(index)
                            val hasFocus = isFocused && index == pin.value.length

                            Box(
                                modifier = Modifier
                                    .size(Dimens.dp(50))
                                    .background(
                                        if (char == null) White else PrimaryColor,
                                        shape = RoundedCornerShape(Dimens.dp8)
                                    )
                                    .border(
                                        width = Dimens.dp(1),
                                        color = if (hasFocus) PrimaryColor else LightGray,
                                        shape = RoundedCornerShape(Dimens.dp8)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (char != null) {
                                    AppText(title = "*", color = White)
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}