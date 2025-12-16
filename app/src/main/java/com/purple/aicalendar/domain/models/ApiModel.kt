package com.purple.aicalendar.domain.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


data class RegisterResponse(
    @SerializedName("isRegistered")
    val isRegistered: Boolean
)

data class RegisterDevice(
    @SerializedName("userId")
    val userId: String,

    @SerializedName("fcmToken")
    val fcmToken: String
)

data class EditRequestBody(
    @SerializedName("request")
    val request: EditRequest
)

data class EditRequest(
    @SerializedName("merchant")
    val merchant: String ?= null,

    @SerializedName("amount")
    val amount: Double ?= null,

    @SerializedName("dueDate")
    val dueDate: String ?= null,

    @SerializedName("account")
    val account: String ?= null,

    @SerializedName("accountName")
    val accountName: String ?= null,

    @SerializedName("description")
    val description: String? = null
)

//data class EventsRequestBody(
//    @SerializedName("request")
//    val request: EventsRequest
//)

data class EventsRequestBody(
    @SerializedName("acceptedItemIds")
    val acceptedItemIds: List<String>,

    @SerializedName("rejectedItemIds")
    val rejectedItemIds: List<String>
)

@Serializable
data class ApiMessageResponse(
    val message: String
)
@Serializable
data class EventResponse(
    val id: String,
    val userId: String,
    val status: String,
    val createdAt: String,
    val items: List<ItemDto>
)

@Serializable
data class ItemDto(
    val id: String,
    val merchant: String?,
    val amount: Double,
    val dueDate: String,
    val explanation: String?,
    val confidence: Double?,
    val pattern: String?,
    val isAccepted: Boolean?,
    val isEdited: Boolean?,
    val account: String?,
    val accountName: String?,
    val description: String?,
)
