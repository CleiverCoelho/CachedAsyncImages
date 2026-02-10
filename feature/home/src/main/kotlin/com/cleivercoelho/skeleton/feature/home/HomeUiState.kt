package com.cleivercoelho.skeleton.feature.home

import com.cleivercoelho.skeleton.core.model.User

data class HomeUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false
)

sealed interface HomeEvent {
    data class NavigateToUserDetail(val userId: Int) : HomeEvent
    data object NavigateToSettings : HomeEvent
    data class ShowSnackbar(val message: String) : HomeEvent
}

sealed interface HomeAction {
    data object Refresh : HomeAction
    data object LoadUsers : HomeAction
    data class DeleteUser(val id: Int) : HomeAction
    data class UserClicked(val userId: Int) : HomeAction
    data object SettingsClicked : HomeAction
    data object ClearError : HomeAction
}
