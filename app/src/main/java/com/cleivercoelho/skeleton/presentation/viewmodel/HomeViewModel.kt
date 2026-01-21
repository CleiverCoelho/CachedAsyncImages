package com.cleivercoelho.skeleton.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.cleivercoelho.skeleton.domain.repository.UserRepository
import com.cleivercoelho.skeleton.domain.usecase.GetUsersUseCase
import com.cleivercoelho.skeleton.presentation.viewmodel.base.BaseViewModel
import com.cleivercoelho.skeleton.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val repository: UserRepository
) : BaseViewModel<HomeUiState, HomeEvent, HomeAction>(HomeUiState()) {

    init {
        onAction(HomeAction.LoadUsers)
        onAction(HomeAction.Refresh)
    }

    override fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.LoadUsers -> loadUsers()
            HomeAction.Refresh -> refreshFromNetwork()
            is HomeAction.DeleteUser -> deleteUser(action.id)
            is HomeAction.UserClicked -> sendEvent(HomeEvent.NavigateToUserDetail(action.userId))
            HomeAction.SettingsClicked -> sendEvent(HomeEvent.NavigateToSettings)
            HomeAction.ClearError -> setState { copy(error = null) }
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            getUsersUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> setState { copy(isLoading = true) }
                    is Resource.Success -> setState {
                        copy(users = result.data, isLoading = false, error = null)
                    }
                    is Resource.Error -> {
                        setState { copy(isLoading = false, error = result.message) }
                        sendEvent(HomeEvent.ShowSnackbar(result.message ?: "Unknown error"))
                    }
                }
            }
        }
    }

    private fun refreshFromNetwork() {
        viewModelScope.launch {
            setState { copy(isRefreshing = true) }
            when (val result = repository.refreshUsers()) {
                is Resource.Success -> setState { copy(isRefreshing = false) }
                is Resource.Error -> {
                    setState { copy(isRefreshing = false, error = result.message) }
                    sendEvent(HomeEvent.ShowSnackbar(result.message ?: "Refresh failed"))
                }
                is Resource.Loading -> Unit
            }
        }
    }

    private fun deleteUser(id: Int) {
        viewModelScope.launch {
            repository.deleteUser(id)
            sendEvent(HomeEvent.ShowSnackbar("User deleted"))
        }
    }
}