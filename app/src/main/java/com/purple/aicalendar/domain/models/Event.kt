package com.purple.aicalendar.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id :String,
    val title: String,
    val amount: String,
    val date: String,
    val type: String,
    val transactionType: String,
    val accuracy: String,
    val billName: String? = null,
    val obligee: String?=null,
    val accountNumber: String? = null,
    val description: String? = null,
)