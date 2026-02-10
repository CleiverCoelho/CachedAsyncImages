package com.cleivercoelho.skeleton.feature.userdetail

import com.cleivercoelho.skeleton.core.model.User

data class UserDetailUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface UserDetailEvent {
    data object NavigateBack : UserDetailEvent
    data class ShowSnackbar(val message: String) : UserDetailEvent
    data class OpenEmail(val email: String) : UserDetailEvent
    data class OpenPhone(val phone: String) : UserDetailEvent
}

sealed interface UserDetailAction {
    data object BackClicked : UserDetailAction
    data object EmailClicked : UserDetailAction
    data object PhoneClicked : UserDetailAction
    data object DeleteClicked : UserDetailAction
    data object RetryClicked : UserDetailAction
}
