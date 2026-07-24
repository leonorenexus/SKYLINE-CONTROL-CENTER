package com.dragonic.skyline.presentation.wifi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

data class WifiUiState(
    val ssid: String              = "DRAGONIC-5G",
    val bssid: String             = "AA:BB:CC:DD:EE:FF",
    val localIp: String           = "192.168.1.105",
    val gateway: String           = "192.168.1.1",
    val dns: String               = "8.8.8.8",
    val signalStrength: Int       = -55,
    val internetConnected: Boolean= true,
    val downloadMbps: Int         = 87,
    val uploadMbps: Int           = 42,
    val pingMs: Int               = 12,
    val downloadHistory: List<Float> = List(20) { 60f + Random.nextFloat() * 40f },
    val uploadHistory: List<Float>   = List(20) { 30f + Random.nextFloat() * 20f }
)

@HiltViewModel
class WifiViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(WifiUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                delay(2000)
                val dl = (60 + Random.nextInt(0, 60)).toFloat()
                val ul = (30 + Random.nextInt(0, 30)).toFloat()
                _uiState.update { s ->
                    s.copy(
                        downloadMbps    = dl.toInt(),
                        uploadMbps      = ul.toInt(),
                        pingMs          = Random.nextInt(8, 30),
                        signalStrength  = -Random.nextInt(40, 75),
                        downloadHistory = (s.downloadHistory.drop(1) + dl).takeLast(20),
                        uploadHistory   = (s.uploadHistory.drop(1) + ul).takeLast(20)
                    )
                }
            }
        }
    }
}
