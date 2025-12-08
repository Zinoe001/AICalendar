package com.purple.aicalendar.core.utils

import java.text.SimpleDateFormat
import java.util.*

object DateExtension {

    private val inputFormats = listOf(
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US),
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US),
        SimpleDateFormat("yyyy-MM-dd", Locale.US)
    ).onEach { it.timeZone = TimeZone.getTimeZone("UTC") }

    private val outputFormat = SimpleDateFormat("MMM dd", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    private val outputFormatWithDay = SimpleDateFormat("EEE, MMM dd", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    fun formatUtcDate(utcDate: String): String {
        val parsed: Date? = inputFormats.firstNotNullOfOrNull { format ->
            try {
                format.parse(utcDate)
            } catch (_: Exception) {
                null
            }
        }
        return parsed?.let { outputFormat.format(it) } ?: ""
    }

    fun formatUtcDateWithDay(utcDate: String): String {
        val parsed: Date? = inputFormats.firstNotNullOfOrNull { format ->
            try {
                format.parse(utcDate)
            } catch (_: Exception) {
                null
            }
        }
        return parsed?.let { outputFormatWithDay.format(it) } ?: ""
    }
}