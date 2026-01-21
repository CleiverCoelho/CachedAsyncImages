package com.cleivercoelho.skeleton.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.cleivercoelho.skeleton.domain.model.User
import com.cleivercoelho.skeleton.domain.repository.UserRepository
import com.cleivercoelho.skeleton.presentation.navigation.Route
import com.cleivercoelho.skeleton.presentation.viewmodel.base.BaseViewModel
import com.cleivercoelho.skeleton.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

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

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repository: UserRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<UserDetailUiState, UserDetailEvent, UserDetailAction>(UserDetailUiState()) {

    private val userId: Int = savedStateHandle.toRoute<Route.UserDetail>().userId

    init {
        loadUser()
    }

    override fun onAction(action: UserDetailAction) {
        when (action) {
            UserDetailAction.BackClicked -> sendEvent(UserDetailEvent.NavigateBack)
            UserDetailAction.EmailClicked -> currentState.user?.email?.let {
                sendEvent(UserDetailEvent.OpenEmail(it))
            }
            UserDetailAction.PhoneClicked -> currentState.user?.phone?.let {
                sendEvent(UserDetailEvent.OpenPhone(it))
            }
            UserDetailAction.DeleteClicked -> deleteUser()
            UserDetailAction.RetryClicked -> loadUser()
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }
            when (val result = repository.getUserById(userId)) {
                is Resource.Success -> setState { copy(user = result.data, isLoading = false) }
                is Resource.Error -> setState { copy(error = result.message, isLoading = false) }
                is Resource.Loading -> Unit
            }
        }
    }

    private fun deleteUser() {
        viewModelScope.launch {
            repository.deleteUser(userId)
            sendEvent(UserDetailEvent.ShowSnackbar("User deleted"))
            sendEvent(UserDetailEvent.NavigateBack)
        }
    }
}