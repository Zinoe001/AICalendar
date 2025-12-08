package com.purple.aicalendar.ui.view.notification.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.ui.theme.Dimens

@Composable
fun PendingList(
    modifier: Modifier = Modifier,
    request:List<Event>
) {
    Column(
        modifier
            .padding(horizontal = Dimens.dp24),
        horizontalAlignment = Alignment.Start
    ){
        request.forEach {event ->
            PendingCard(event=event)
        }
    }
}