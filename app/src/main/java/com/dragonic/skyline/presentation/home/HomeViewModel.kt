package com.dragonic.skyline.presentation.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

data class HomeUiState(
    val username: String       = "Ren",
    val bgVideoUri: Uri?       = null,
    val bannerUri: Uri?        = null,
    val ssid: String           = "DRAGONIC-5G",
    val downloadMbps: Int      = 87,
    val uploadMbps: Int        = 42,
    val pingMs: Int            = 12,
    val deviceCount: Int       = 5,
    val activityCount: Int     = 128,
    val internetConnected: Boolean = true
)

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        startRealtimeUpdates()
    }

    private fun startRealtimeUpdates() {
        viewModelScope.launch {
            while (true) {
                delay(3000)
                _uiState.update {
                    it.copy(
                        downloadMbps  = Random.nextInt(60, 120),
                        uploadMbps    = Random.nextInt(30, 60),
                        pingMs        = Random.nextInt(8, 25),
                        activityCount = it.activityCount + Random.nextInt(0, 3)
                    )
                }
            }
        }
    }

    fun onBannerSelected(uri: Uri) {
        _uiState.update { it.copy(bannerUri = uri) }
    }

    fun onBannerRemoved() {
        _uiState.update { it.copy(bannerUri = null) }
    }

    fun onBgSelected(uri: Uri) {
        _uiState.update { it.copy(bgVideoUri = uri) }
    }
}
