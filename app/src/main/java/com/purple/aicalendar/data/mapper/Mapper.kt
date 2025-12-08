package com.purple.aicalendar.data.mapper

import com.purple.aicalendar.data.dto.EventEntity
import com.purple.aicalendar.domain.models.Event

fun Event.toEntity(): EventEntity{
    return EventEntity(
        uid = id,
        title = title,
        amount = amount,
        date = date,
        type = type,
        transactionType = transactionType,
        accuracy = accuracy,
        billName = billName,
        obligee= obligee,
        accountNumber = accountNumber,
        description = description
    )
}

fun EventEntity.toEvent(): Event{
    return Event(
        id = uid,
        title = title?:"",
        type = type?:"",
        amount = amount?:"",
        date = date?:"",
        accuracy = accuracy?:"",
        transactionType = transactionType?:"",
        billName = billName,
        obligee= obligee,
        accountNumber = accountNumber,
        description = description
    )
}