package com.cleivercoelho.skeleton.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleivercoelho.skeleton.core.common.result.Resource
import com.cleivercoelho.skeleton.core.domain.usecase.DeleteUserUseCase
import com.cleivercoelho.skeleton.core.domain.usecase.GetUsersUseCase
import com.cleivercoelho.skeleton.core.domain.usecase.RefreshUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val refreshUsersUseCase: RefreshUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _events = Channel<HomeEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        onAction(HomeAction.LoadUsers)
        onAction(HomeAction.Refresh)
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.LoadUsers -> loadUsers()
            HomeAction.Refresh -> refreshFromNetwork()
            is HomeAction.DeleteUser -> deleteUser(action.id)
            is HomeAction.UserClicked -> sendEvent(HomeEvent.NavigateToUserDetail(action.userId))
            HomeAction.SettingsClicked -> sendEvent(HomeEvent.NavigateToSettings)
            HomeAction.ClearError -> _uiState.update { it.copy(error = null) }
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            getUsersUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Resource.Success -> _uiState.update {
                        it.copy(users = result.data, isLoading = false, error = null)
                    }
                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false, error = result.message) }
                        sendEvent(HomeEvent.ShowSnackbar(result.message))
                    }
                }
            }
        }
    }

    private fun refreshFromNetwork() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            when (val result = refreshUsersUseCase()) {
                is Resource.Success -> _uiState.update { it.copy(isRefreshing = false) }
                is Resource.Error -> {
                    _uiState.update { it.copy(isRefreshing = false, error = result.message) }
                    sendEvent(HomeEvent.ShowSnackbar(result.message))
                }
                is Resource.Loading -> Unit
            }
        }
    }

    private fun deleteUser(id: Int) {
        viewModelScope.launch {
            deleteUserUseCase(id)
            sendEvent(HomeEvent.ShowSnackbar("User deleted"))
        }
    }

    private fun sendEvent(event: HomeEvent) {
        viewModelScope.launch { _events.send(event) }
    }
}
