package com.purple.aicalendar.data.api

import com.purple.aicalendar.domain.models.ApiMessageResponse
import com.purple.aicalendar.domain.models.CalendarResponse
import com.purple.aicalendar.domain.models.EditRequest
import com.purple.aicalendar.domain.models.EditRequestBody
import com.purple.aicalendar.domain.models.EventResponse
import com.purple.aicalendar.domain.models.EventsRequestBody
import com.purple.aicalendar.domain.models.RegisterDevice
import com.purple.aicalendar.domain.models.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Retrofit API service interface for interacting with the Dragon Firestore database.
 * Defines the HTTP endpoints for note-related operations.
 */
interface AICalenderApiServices {
    @GET("api/push-notifications/check-device/{id}")
    suspend fun checkDevice(
        @Path("id") id: String,
    ): Response<RegisterResponse>

    @POST("api/push-notifications/register-device")
    suspend fun registerDevice(
        @Body request: RegisterDevice
    ): Response<ApiMessageResponse>

    @GET("/api/predictions/user/{id}/items-limit")
    suspend fun getItemsLimit(
        @Path("id") id: String,
    ): List<EventResponse>

    @GET("api/predictions/user/{id}/month/{year}/{month}")
    suspend fun getEvents(
        @Path("id") id: String,
        @Path("year") year: Int,
        @Path("month") month: Int
    ): List<EventResponse>

    @PUT("api/predictions/items/{id}")
    suspend fun editEvents(
        @Path("id") id: String,
        @Body request: EditRequestBody
    ):Response<ApiMessageResponse>

    @POST("api/predictions/batch-process")
    suspend fun postEvents(
        @Body request: EventsRequestBody
    ): Response<ApiMessageResponse>

    @GET("api/calendar/user/{id}")
    suspend fun getCalendar(
        @Path("id") id: String,
    ): Response<CalendarResponse>

    @PUT("api/calendar/items/{id}")
    suspend fun editCalendarEvents(
        @Path("id") id: String,
        @Body request: EditRequest
    ):Response<ApiMessageResponse>
}