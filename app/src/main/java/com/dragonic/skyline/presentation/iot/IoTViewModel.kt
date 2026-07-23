package com.dragonic.skyline.presentation.iot

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.lifecycle.ViewModel
import com.dragonic.skyline.core.theme.SkylineColors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class IoTViewModel @Inject constructor() : ViewModel() {
    private val _devices = MutableStateFlow(initialDevices())
    val devices = _devices.asStateFlow()

    fun toggle(id: Int) {
        _devices.update { list ->
            list.map { if (it.id == id) it.copy(isOn = !it.isOn) else it }
        }
    }

    private fun initialDevices() = listOf(
        IoTDevice(1,  "Lampu Ruang Tamu", "Smart Lamp",  Icons.Rounded.LightbulbCircle, isOn = true,  value = "2700K • 100%",   color = SkylineColors.StatusWarning),
        IoTDevice(2,  "Lampu Kamar",      "Smart Lamp",  Icons.Rounded.Lightbulb,       isOn = false, value = "6500K • 60%",    color = SkylineColors.CyberCyan),
        IoTDevice(3,  "Smart Plug A",     "Smart Plug",  Icons.Rounded.Power,           isOn = true,  value = "2.4 A • 540 W",  color = SkylineColors.SakuraPink),
        IoTDevice(4,  "Smart Plug B",     "Smart Plug",  Icons.Rounded.Outlet,          isOn = false, value = "0 W",            color = SkylineColors.SakuraPink),
        IoTDevice(5,  "Relay 1",          "Relay",       Icons.Rounded.ToggleOn,        isOn = true,  value = "Channel 1",      color = SkylineColors.NeonViolet),
        IoTDevice(6,  "Relay 2",          "Relay",       Icons.Rounded.ToggleOff,       isOn = false, value = "Channel 2",      color = SkylineColors.NeonViolet),
        IoTDevice(7,  "Sensor Suhu",      "Sensor",      Icons.Rounded.Thermostat,      isOn = true,  value = "28.5°C • 65%RH", color = SkylineColors.StatusOnline),
        IoTDevice(8,  "Sensor Gerak",     "Sensor",      Icons.Rounded.Sensors,         isOn = true,  value = "Tidak ada gerak",color = SkylineColors.StatusInfo)
    )
}
