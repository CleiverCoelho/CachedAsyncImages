package com.cleivercoelho.skeleton.presentation.viewmodel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE, EVENT, ACTION>(
    initialState: STATE
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<STATE> = _uiState.asStateFlow()

    private val _events = Channel<EVENT>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    protected val currentState: STATE get() = _uiState.value

    abstract fun onAction(action: ACTION)

    protected fun setState(reducer: STATE.() -> STATE) {
        _uiState.update { it.reducer() }
    }

    protected fun sendEvent(event: EVENT) {
        viewModelScope.launch { _events.send(event) }
    }
}