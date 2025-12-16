package com.purple.aicalendar.data.mapper

import com.purple.aicalendar.data.dto.EventEntity
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.domain.models.ItemDto
import java.util.Locale

fun Event.toEntity(): EventEntity{
    return EventEntity(
        uid = id,
        title = title,
        amount = amount,
        date = date,
        transactionType = transactionType,
        accuracy = accuracy,
        obligee= obligee,
        accountNumber = accountNumber,
        description = description
    )
}

fun EventEntity.toEvent(): Event{
    return Event(
        id = uid,
        title = title?:"",
        amount = amount?:"",
        date = date?:"",
        accuracy = accuracy?:"",
        transactionType = transactionType?:"",
        obligee= obligee,
        accountNumber = accountNumber,
        description = description
    )
}

fun ItemDto.toEvent() = Event(
    id = id,
    title = merchant ?: "",
    amount = amount.toString(),
    date = dueDate,
    transactionType = if (account != null) "Transfer" else "Bill",
    accuracy = String.format(Locale.US, "%.0f", (confidence?.toDouble() ?: 0.0) * 100),
    obligee = accountName,
    accountNumber = account,
    description = description,
)