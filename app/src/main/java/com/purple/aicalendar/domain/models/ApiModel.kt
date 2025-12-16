package com.purple.aicalendar.domain.models

data class RegisterDevice(
    val userId: String,
    val fcmToken: String
)