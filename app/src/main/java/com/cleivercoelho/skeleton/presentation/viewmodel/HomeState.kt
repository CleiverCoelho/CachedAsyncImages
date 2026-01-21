package com.cleivercoelho.skeleton.presentation.viewmodel

import com.cleivercoelho.skeleton.domain.model.User

data class HomeUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false
)

// Events - one-time UI effects (navigation, snackbar, etc.)
sealed interface HomeEvent {
    data class NavigateToUserDetail(val userId: Int) : HomeEvent
    data object NavigateToSettings : HomeEvent
    data class ShowSnackbar(val message: String) : HomeEvent
}

// Actions - user interactions
sealed interface HomeAction {
    data object Refresh : HomeAction
    data object LoadUsers : HomeAction
    data class DeleteUser(val id: Int) : HomeAction
    data class UserClicked(val userId: Int) : HomeAction
    data object SettingsClicked : HomeAction
    data object ClearError : HomeAction
}