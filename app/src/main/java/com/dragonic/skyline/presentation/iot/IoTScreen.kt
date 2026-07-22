package com.dragonic.skyline.presentation.iot

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dragonic.skyline.core.theme.SkylineColors
import com.dragonic.skyline.presentation.components.*

data class IoTDevice(
    val id: Int,
    val name: String,
    val type: String,
    val icon: ImageVector,
    val isOn: Boolean,
    val value: String = "",
    val color: Color = SkylineColors.SakuraPink
)

@Composable
fun IoTScreen(
    navController: NavController,
    viewModel: IoTViewModel = hiltViewModel()
) {
    val devices by viewModel.devices.collectAsState()

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = { SkylineBottomNavBar(navController) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            NebulaBackground(Modifier.fillMaxSize())
            CyberGridBackground(Modifier.fillMaxSize())
            Box(Modifier.fillMaxSize().background(SkylineColors.DeepVoid.copy(alpha = 0.6f)))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("IoT DASHBOARD", style = MaterialTheme.typography.headlineMedium, color = SkylineColors.SakuraPink)
                Text("Kontrol perangkat pintar", style = MaterialTheme.typography.bodySmall, color = SkylineColors.TextMuted)

                // Summary row
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val onCount = devices.count { it.isOn }
                    SummaryChip("${devices.size} Perangkat", Icons.Rounded.Devices, SkylineColors.SakuraPink, Modifier.weight(1f))
                    SummaryChip("$onCount Aktif", Icons.Rounded.PowerSettingsNew, SkylineColors.StatusOnline, Modifier.weight(1f))
                }

                // Device grid
                LazyVerticalGrid(
                    columns              = GridCells.Fixed(2),
                    modifier             = Modifier.heightIn(max = 600.dp),
                    contentPadding       = PaddingValues(0.dp),
                    horizontalArrangement= Arrangement.spacedBy(10.dp),
                    verticalArrangement  = Arrangement.spacedBy(10.dp),
                    userScrollEnabled    = false
                ) {
                    items(devices, key = { it.id }) { device ->
                        IoTDeviceCard(
                            device    = device,
                            onToggle  = { viewModel.toggle(device.id) }
                        )
                    }
                }

                // Schedule Card
                GlassCard(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Rounded.Schedule, null, tint = SkylineColors.CyberCyan, modifier = Modifier.size(20.dp))
                            Text("JADWAL OTOMATIS", style = MaterialTheme.typography.labelLarge, color = SkylineColors.CyberCyan)
                        }
                        Spacer(Modifier.height(8.dp))
                        Text("Atur jadwal on/off perangkat otomatis",
                            style = MaterialTheme.typography.bodySmall,
                            color = SkylineColors.TextMuted)
                        Spacer(Modifier.height(8.dp))
                        Button(
                            onClick = {},
                            colors  = ButtonDefaults.buttonColors(containerColor = SkylineColors.CyberCyan.copy(alpha = 0.2f)),
                            border  = BorderStroke(1.dp, SkylineColors.CyberCyan.copy(alpha = 0.5f)),
                            modifier= Modifier.fillMaxWidth()
                        ) {
                            Text("+ Tambah Jadwal", color = SkylineColors.CyberCyan)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SummaryChip(
    label: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    GlassCard(modifier = modifier, borderColor = color.copy(alpha = 0.4f)) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, null, tint = color, modifier = Modifier.size(18.dp))
            Text(label, style = MaterialTheme.typography.labelMedium, color = color)
        }
    }
}

@Composable
private fun IoTDeviceCard(device: IoTDevice, onToggle: () -> Unit) {
    val inf = rememberInfiniteTransition(label = "iot_card")
    val glow by inf.animateFloat(0.3f, 0.8f,
        infiniteRepeatable(tween(2000, easing = EaseInOutSine), RepeatMode.Reverse), "iot_glow")

    val activeColor = if (device.isOn) device.color else SkylineColors.TextMuted

    GlassCard(
        modifier    = Modifier.fillMaxWidth(),
        borderColor = activeColor.copy(alpha = if (device.isOn) 0.6f else 0.2f),
        glowColor   = if (device.isOn) device.color.copy(alpha = glow * 0.3f) else Color.Transparent,
        animated    = device.isOn
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(activeColor.copy(alpha = if (device.isOn) 0.2f else 0.08f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(device.icon, null, tint = activeColor, modifier = Modifier.size(24.dp))
                }
                Switch(
                    checked = device.isOn,
                    onCheckedChange = { onToggle() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor  = Color.White,
                        checkedTrackColor  = device.color,
                        uncheckedThumbColor= SkylineColors.TextMuted,
                        uncheckedTrackColor= SkylineColors.GlassWhite
                    )
                )
            }
            Text(device.name, style = MaterialTheme.typography.labelLarge, color = SkylineColors.TextPrimary)
            Text(device.type, style = MaterialTheme.typography.labelSmall, color = SkylineColors.TextMuted)
            if (device.value.isNotEmpty()) {
                Text(device.value, style = MaterialTheme.typography.bodySmall, color = activeColor)
            }

            // Status badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(activeColor.copy(alpha = 0.15f))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    if (device.isOn) "AKTIF" else "MATI",
                    style = MaterialTheme.typography.labelSmall,
                    color = activeColor
                )
            }
        }
    }
}
