package com.purple.aicalendar.data.api

import com.purple.aicalendar.domain.models.Event
import retrofit2.Response
import retrofit2.http.GET

/**
 * Retrofit API service interface for interacting with the Dragon Firestore database.
 * Defines the HTTP endpoints for note-related operations.
 */
interface AICalenderApiServices {
    /**
     * Fetches a list of notes from the Firestore database.
     * This function makes a GET request to the Firestore REST API endpoint for the 'notes' collection.
     *
     * @return A [Response] object containing a [Event], which wraps the list of notes.
     * The response will be successful if the notes are fetched correctly, otherwise it will contain an error.
     */
    @GET("events/today")
    suspend fun getEvents(): List<Event>

}