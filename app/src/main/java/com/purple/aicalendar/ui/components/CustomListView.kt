package com.purple.aicalendar.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> CustomListView(
    modifier: Modifier = Modifier,
    items: List<T>,
    content: @Composable (T) -> Unit
) {
    Column(modifier = modifier) {
        items.forEach { item ->
            content(item)
        }
    }
}

@Composable
fun <T> CustomRowListView(
    modifier: Modifier = Modifier,
    items: List<T>,
    content: @Composable (T) -> Unit
) {
    Row(modifier = modifier) {
        items.forEach { item ->
            content(item)
        }
    }
}