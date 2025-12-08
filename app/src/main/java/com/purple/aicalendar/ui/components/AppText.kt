package com.purple.aicalendar.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.purple.aicalendar.ui.theme.Black
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.inter

/**
 * A customizable text component for the application, built on top of [Text].
 *
 * This composable provides a standardized text element with default styling
 * consistent with the app's design system (e.g., `jost` font family).
 *
 * @param modifier The [Modifier] to be applied to this text.
 * @param align The alignment of the text within its container. Defaults to [TextAlign.Start].
 * @param title The text to be displayed.
 * @param color The color of the text. Defaults to [Color.Black].
 * @param maxLines The maximum number of lines for the text. Defaults to [Int.MAX_VALUE].
 * @param overflow How visual overflow should be handled. Defaults to [TextOverflow.Ellipsis].
 * @param fontWeight The weight of the font. Defaults to [FontWeight.W500].
 * @param fontSize The size of the font. Defaults to 16.sp.
 * @param fontFamily The font family to be used. Defaults to the app's `jost` font.
 * @param lineHeight The line height for the text.
 */
@Composable
fun AppText(
    modifier: Modifier = Modifier,
    align: TextAlign = TextAlign.Start,
    title: String = "",
    color: Color = Black,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    fontWeight : FontWeight = FontWeight.W500,
    fontSize: TextUnit = Dimens.sp(16F),
    fontFamily: FontFamily = inter,
    lineHeight: TextUnit = fontSize * 1.3f,
    shadow: Shadow? = null
){
        Text(
            textAlign = align,
            modifier = modifier,
            text = title,
            style = TextStyle(
                color = color,
                fontSize = fontSize,
                fontFamily = fontFamily,
                fontWeight = fontWeight,
                lineHeight = lineHeight,
                shadow = shadow
            ),
            overflow = overflow,
            maxLines = maxLines,
        )
    }