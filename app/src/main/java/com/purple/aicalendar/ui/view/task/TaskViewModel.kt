package com.purple.aicalendar.ui.view.task

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purple.aicalendar.core.utils.ScreenState
import com.purple.aicalendar.domain.models.Event
import com.purple.aicalendar.domain.usecase.GetAllTaskUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.onSuccess

@HiltViewModel
class TaskViewModel  @Inject constructor(
    private val taskUseCase : GetAllTaskUsecase
) : ViewModel() {

    private var autoSaveJob: Job? = null
    // This will hold a reference to a function assigned from outside
    var deleteAllTask: (() -> Unit)? = null

    private val _uiState = MutableStateFlow<ScreenState>(ScreenState.Initial)
    var uiState = _uiState.asStateFlow()
    private val _uiSecondaryState = MutableStateFlow<ScreenState>(ScreenState.Initial)
    var uiSecondaryState = _uiSecondaryState.asStateFlow()
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    var event = _events.asStateFlow()
    private val _keptEvents = MutableStateFlow<List<Event>>(emptyList())
    val keptEvents = _keptEvents.asStateFlow()
    private val _discardedEvents = MutableStateFlow<List<Event>>(emptyList())
    val discardedEvents = _discardedEvents.asStateFlow()
    private val _totalItems = MutableStateFlow(0)
    val totalItems = _totalItems.asStateFlow()

    fun keepEvent(event: Event) {
        _keptEvents.update { it + event }
        _events.update { current  -> current  - event }
        scheduleAutoSave()
    }

    fun discardEvent(event: Event) {
        _discardedEvents.update { it + event }
        _events.update { current  -> current  - event }
        scheduleAutoSave()
    }

    fun restoreEvent(event: Event) {
        _discardedEvents.update { it - event }
        _events.update { current  -> listOf(event) + current  }
        scheduleAutoSave()
    }

    fun init() {
        viewModelScope.launch {
            val keptCached = taskUseCase.getKeptEvents()
            val discardedCached = taskUseCase.getDiscardedEvents()
            val predictionCached = taskUseCase.getPredictionEvents()
            _totalItems.value = predictionCached.size + keptCached.size + discardedCached.size
            _keptEvents.value = keptCached
            _discardedEvents.value = discardedCached
            _events.value = predictionCached
            if (predictionCached.isEmpty() && keptCached.isEmpty() && discardedCached.isEmpty()) {
                getPrediction()
            } else {
                Log.d("TaskViewModel", "init: Loaded from cache ${predictionCached.size},${keptCached.size},${discardedCached.size}")
                setTaskState(ScreenState.Success("Loaded from cache"))
            }
        }
    }

    fun getPrediction(){
        viewModelScope.launch {
            setTaskState(ScreenState.LoadingState)
            delay(500)
            taskUseCase.getPredictions()
                .onSuccess { events ->
                    _events.value = events
                    _totalItems.value = events.size
                    setTaskState(ScreenState.Success("Success"))
                }
                .onFailure {
                    val errorMessage = it.message ?: "Something went wrong"
                    setTaskState(ScreenState.Error(errorMessage))
                }
        }
    }
    fun postEvent(){
        viewModelScope.launch {
            setSecondaryTaskState(ScreenState.LoadingState)
            Log.d("TaskViewModel", "postEvent: ${_keptEvents.value},${_discardedEvents.value}")
            delay(5000)
            taskUseCase.postEvents(_keptEvents.value,_discardedEvents.value)
                .onSuccess {
                    setSecondaryTaskState(ScreenState.Success("Success"))
                }
                .onFailure {
                    val errorMessage = it.message ?: "Something went wrong"
                    setSecondaryTaskState(ScreenState.Error(errorMessage))
                }
        }
    }
    fun setAllEvents(list: List<Event>) {
        val keptIds = _keptEvents.value.map { it.id }.toSet()
        val discardedIds = _discardedEvents.value.map { it.id }.toSet()

        // Remove events that exist in kept OR discarded
        val filtered = list.filter { it.id !in keptIds && it.id !in discardedIds }

        _events.value = filtered
    }
    private fun setTaskState(state: ScreenState){
        _uiState.update { state }
    }
    private  fun deleteEvents() {
        // Call the assigned function if available
        deleteAllTask?.invoke()
    }
    private fun setSecondaryTaskState(state: ScreenState){
        _uiSecondaryState.update { state }
    }

    private fun scheduleAutoSave() {
        autoSaveJob?.cancel()
        autoSaveJob = viewModelScope.launch {
            delay(500) // wait for user to finish interacting
            taskUseCase.saveEvents(kept=_keptEvents.value, discarded = _discardedEvents.value, predictions = _events.value)
        }
        deleteEvents()
    }
}