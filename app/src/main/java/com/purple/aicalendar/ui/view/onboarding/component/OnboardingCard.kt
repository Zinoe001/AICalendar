package com.purple.aicalendar.ui.view.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.purple.aicalendar.ui.components.AppText
import com.purple.aicalendar.ui.components.Gap
import com.purple.aicalendar.ui.theme.Black
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.MediumGray
import com.purple.aicalendar.ui.theme.SecondaryColor

@Composable
fun OnboardingCard(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    description: String,
){
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier
                .size(Dimens.dp(50))
                .background(SecondaryColor, RoundedCornerShape(Dimens.dp12)),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier
                    .size(Dimens.dp20) // set a fixed size
            )
        }
        Gap.W(Dimens.dp16)
        Column{
            AppText(
                title = title,
                fontSize = Dimens.sp(16F),
                fontWeight = FontWeight.Bold,
                color = Black
            )
            Gap.H(Dimens.dp8)
            AppText(
                title = description,
                fontSize = Dimens.sp(12),
                fontWeight = FontWeight.W400,
                maxLines = 2,
                color = MediumGray,
            )
        }
    }
}