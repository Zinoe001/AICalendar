package com.purple.aicalendar.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.aicalendar.core.utils.ScreenState
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.domain.usecase.GetAllEventsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val eventsUseCase : GetAllEventsUsecase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ScreenState>(ScreenState.Initial)
    var uiState = _uiState.asStateFlow()
    private val _todayEvents = MutableStateFlow<List<Event>>(emptyList())
    var todayEvents = _todayEvents.asStateFlow()
    private val _upcomingEvents = MutableStateFlow<List<Event>>(emptyList())
    var upcomingEvents = _upcomingEvents.asStateFlow()
    private val _allEvents = MutableStateFlow<List<Event>>(emptyList())
    var allEvents = _allEvents.asStateFlow()
    private val _predictedEvents = MutableStateFlow<List<Event>>(emptyList())
    var predictedEvents = _predictedEvents.asStateFlow()
    var isEventsLoaded = MutableStateFlow(false)

    fun getTodayEvents(){
        viewModelScope.launch {
            eventsUseCase.getTodayEvents()
                .onSuccess { events ->
                    _todayEvents.value = events
                }
                .onFailure {
//                    val errorMessage = it.message ?: "Something went wrong"
                }
        }
    }
    fun getUpcomingEvents(){
        viewModelScope.launch {
            eventsUseCase.getUpcomingEvents()
                .onSuccess { events ->
                    _upcomingEvents.value = events
                }
                .onFailure {
//                    val errorMessage = it.message ?: "Something went wrong"
                }
        }
    }

    fun getAllEvents(isPrediction:Boolean = false){
        viewModelScope.launch {
            eventsUseCase.getAllEvents()
                .onSuccess { events ->
                    if(isPrediction){
                        _predictedEvents.value = events
                        markEventsAsPending()
                        Log.d("SharedViewModel", "Getting and marking all pending events")
                    }
                    else{
                    _allEvents.value = events
                    isEventsLoaded.value = true
                     Log.d("SharedViewModel", "getAllEvents: ${events.size}")

                    }
                }
                .onFailure {
//                    val errorMessage = it.message ?: "Something went wrong"
                }
        }
    }

    fun editEvent(
        id: String,
        amount: String,
        date: String,
        transactionType: String,
        name: String,
        number: String,
        title: String,
        description: String,
    ){
        viewModelScope.launch {
            setSharedState(ScreenState.LoadingState)
            delay(2000)
            eventsUseCase.editEvents(
                id =id,
                amount = amount,
                title = title,
                date = date,
                transactionType = transactionType,
                name = name,
                number = number,
                description = description
            )
                .onSuccess {
                    setSharedState(ScreenState.Success("Success"))
                }
                .onFailure {
                    val errorMessage = it.message ?: "Something went wrong"
                    setSharedState(ScreenState.Error(errorMessage))
                }
        }
    }
    fun deleteEvents() {
       viewModelScope.launch {
            eventsUseCase.deleteAllEvents()
        }
    }
    fun deletePendingEvents() {
        viewModelScope.launch {
            try {
                eventsUseCase.deleteAllPendingEvents() // suspend function
                getAllEvents() // fetch fresh data
                isEventsLoaded.value = true
            } catch (e: Exception) {
                Log.e("SharedViewModel", "Failed to delete pending events", e)
            }
        }
    }

    private fun setSharedState(state: ScreenState){
        _uiState.update { state }
    }

  fun markEventsAsPending() = viewModelScope.launch {
        Log.d("SharedViewModel", "marking events as pending")
        eventsUseCase.markEventsAsPending()
    }

}