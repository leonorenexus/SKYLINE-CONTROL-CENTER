package com.dragonic.skyline.presentation.login

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val error: String? = null,
    val bgVideoUri: Uri? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onUsernameChange(value: String) {
        _uiState.update { it.copy(username = value, error = null) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, error = null) }
    }

    fun onRememberMeChange(value: Boolean) {
        _uiState.update { it.copy(rememberMe = value) }
    }

    fun onLogin() {
        viewModelScope.launch {
            val state = _uiState.value
            if (state.username.isBlank() || state.password.isBlank()) {
                _uiState.update { it.copy(error = "Username dan password wajib diisi") }
                return@launch
            }
            _uiState.update { it.copy(isLoading = true, error = null) }
            delay(1500) // Simulate API call
            // Demo: any non-empty credentials pass
            _uiState.update { it.copy(isLoading = false, loginSuccess = true) }
        }
    }

    fun onBiometricLogin() {
        // Trigger biometric from Activity context — handled by composable
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(500)
            _uiState.update { it.copy(isLoading = false, loginSuccess = true) }
        }
    }
}
