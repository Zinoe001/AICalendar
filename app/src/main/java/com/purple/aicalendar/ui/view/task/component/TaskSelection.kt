package com.purple.aicalendar.ui.view.task.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.ui.components.AppText
import com.purple.aicalendar.ui.components.CustomRowListView
import com.purple.aicalendar.ui.components.Gap
import com.purple.aicalendar.ui.theme.Black
import com.purple.aicalendar.ui.theme.DarkGray
import com.purple.aicalendar.ui.theme.Dimens
import com.purple.aicalendar.ui.theme.LightGray
import com.purple.aicalendar.ui.theme.MediumGray
import com.purple.aicalendar.ui.theme.PrimaryColor

@Composable
fun TaskSelection(
    modifier: Modifier = Modifier,
    items: List<Event>,
    discardItems: List<Event>,
    isDiscarded: Boolean,
    kept: Int,
    discarded: Int,
    totalItems: Int,
    onKeep: (Event) -> Unit,
    onDiscard: (Event) -> Unit,
    onEditClick: (Event) -> Unit,
    onRestore: (Event) -> Unit,
    onDiscardTap:() -> Unit,
    onKeptTap:() -> Unit,
){
    Column{
        Gap.H(Dimens.dp24)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppText(
                title = "${kept + discarded} of $totalItems",
                color = DarkGray,
                fontSize = Dimens.sp(16F),
                fontWeight = FontWeight.W500)
            Spacer(modifier = Modifier.weight(1F))
            Box(
                modifier
                    .size(Dimens.dp8)
                    .background(PrimaryColor, CircleShape))
            Gap.W(Dimens.dp4)
            AppText(modifier
                .clickable { onKeptTap() },
                title = "$kept Kept",
                color = PrimaryColor,
                fontSize = Dimens.sp(12F),
                fontWeight = FontWeight.W500)
            Gap.W(Dimens.dp8)
            Box(
                modifier
                    .size(Dimens.dp8)
                    .background(LightGray, CircleShape))
            Gap.W(Dimens.dp4)
            AppText(
                modifier
                    .clickable { onDiscardTap() },
                title = "$discarded discarded",
                color = MediumGray,
                fontSize = Dimens.sp(12F),
                fontWeight = FontWeight.W500)
        }
        Gap.H(Dimens.dp16)
        ProgressIndicatorBar(progress = ((kept.toFloat()+discarded.toFloat()) / totalItems.toFloat()))
        Gap.H(Dimens.dp(40))
        if(isDiscarded){
            if(discardItems.isNotEmpty()) {
                CustomRowListView(items = discardItems) { item ->
                    TaskCard(
                        event = item,
                        isDiscarded = true,
                        onRestore = { onRestore(item) },
                        onEditClick = { onEditClick(item) }
                    )
                }
            }else{
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    AppText(
                        title = "No discarded tasks",
                        color = Black,
                        fontSize = Dimens.sp(16),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }else{
             CustomRowListView(items = items) {
                    item ->   TaskCard(
                 event = item,
                 onKeep = { onKeep(item) },
                 onDiscard = { onDiscard(item) },
                 onEditClick = { onEditClick(item) }
                    )
             }
        }

    }
}