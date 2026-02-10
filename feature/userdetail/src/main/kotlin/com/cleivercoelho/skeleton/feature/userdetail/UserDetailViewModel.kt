package com.cleivercoelho.skeleton.feature.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.cleivercoelho.skeleton.core.common.result.Resource
import com.cleivercoelho.skeleton.core.domain.usecase.DeleteUserUseCase
import com.cleivercoelho.skeleton.core.domain.usecase.GetUserByIdUseCase
import com.cleivercoelho.skeleton.core.navigation.Route
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
class UserDetailViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val userId: Int = savedStateHandle.toRoute<Route.UserDetail>().userId

    private val _uiState = MutableStateFlow(UserDetailUiState())
    val uiState: StateFlow<UserDetailUiState> = _uiState.asStateFlow()

    private val _events = Channel<UserDetailEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        loadUser()
    }

    fun onAction(action: UserDetailAction) {
        when (action) {
            UserDetailAction.BackClicked -> sendEvent(UserDetailEvent.NavigateBack)
            UserDetailAction.EmailClicked -> _uiState.value.user?.email?.let {
                sendEvent(UserDetailEvent.OpenEmail(it))
            }
            UserDetailAction.PhoneClicked -> _uiState.value.user?.phone?.let {
                sendEvent(UserDetailEvent.OpenPhone(it))
            }
            UserDetailAction.DeleteClicked -> deleteUser()
            UserDetailAction.RetryClicked -> loadUser()
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getUserByIdUseCase(userId).collect { result ->
                when (result) {
                    is Resource.Success -> _uiState.update {
                        it.copy(user = result.data, isLoading = false)
                    }
                    is Resource.Error -> _uiState.update {
                        it.copy(error = result.message, isLoading = false)
                    }
                    is Resource.Loading -> Unit
                }
            }
        }
    }

    private fun deleteUser() {
        viewModelScope.launch {
            deleteUserUseCase(userId)
            sendEvent(UserDetailEvent.ShowSnackbar("User deleted"))
            sendEvent(UserDetailEvent.NavigateBack)
        }
    }

    private fun sendEvent(event: UserDetailEvent) {
        viewModelScope.launch { _events.send(event) }
    }
}
