package com.dragonic.skyline.presentation.devices

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavController
import com.dragonic.skyline.core.theme.SkylineColors
import com.dragonic.skyline.presentation.components.*

data class DeviceItem(
    val id: Int,
    val name: String,
    val type: String,
    val icon: ImageVector,
    val status: String,
    val ip: String,
    val color: Color
)

@Composable
fun DevicesScreen(navController: NavController) {
    val devices = remember {
        listOf(
            DeviceItem(1, "Phone - Redmi 15C", "Android", Icons.Rounded.PhoneAndroid,     "Online",  "192.168.1.101", SkylineColors.StatusOnline),
            DeviceItem(2, "Laptop Asus",        "Windows", Icons.Rounded.LaptopWindows,    "Online",  "192.168.1.102", SkylineColors.CyberCyan),
            DeviceItem(3, "Smart TV",           "IoT",     Icons.Rounded.Tv,               "Offline", "192.168.1.103", SkylineColors.TextMuted),
            DeviceItem(4, "Router DRAGONIC",    "Network", Icons.Rounded.Router,           "Online",  "192.168.1.1",   SkylineColors.StatusWarning),
            DeviceItem(5, "ESP32 Node",         "IoT",     Icons.Rounded.Memory,           "Online",  "192.168.1.200", SkylineColors.SakuraPink)
        )
    }

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
                Text("PERANGKAT", style = MaterialTheme.typography.headlineMedium, color = SkylineColors.SakuraPink)
                Text("Semua perangkat terdaftar", style = MaterialTheme.typography.bodySmall, color = SkylineColors.TextMuted)

                // Summary
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    val onlineCount = devices.count { it.status == "Online" }
                    GlassCard(Modifier.weight(1f)) {
                        Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("${devices.size}", style = MaterialTheme.typography.headlineMedium, color = SkylineColors.SakuraPink)
                            Text("Total", style = MaterialTheme.typography.labelSmall, color = SkylineColors.TextMuted)
                        }
                    }
                    GlassCard(Modifier.weight(1f)) {
                        Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("$onlineCount", style = MaterialTheme.typography.headlineMedium, color = SkylineColors.StatusOnline)
                            Text("Online", style = MaterialTheme.typography.labelSmall, color = SkylineColors.TextMuted)
                        }
                    }
                    GlassCard(Modifier.weight(1f)) {
                        Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("${devices.size - onlineCount}", style = MaterialTheme.typography.headlineMedium, color = SkylineColors.StatusOffline)
                            Text("Offline", style = MaterialTheme.typography.labelSmall, color = SkylineColors.TextMuted)
                        }
                    }
                }

                // Device list
                devices.forEach { device ->
                    DeviceCard(device)
                }

                // Add device button
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, SkylineColors.SakuraPink.copy(alpha = 0.5f)),
                    shape  = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Rounded.Add, null, tint = SkylineColors.SakuraPink)
                    Spacer(Modifier.width(8.dp))
                    Text("Tambah Perangkat", color = SkylineColors.SakuraPink)
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun DeviceCard(device: DeviceItem) {
    GlassCard(
        Modifier.fillMaxWidth(),
        borderColor = device.color.copy(alpha = if (device.status == "Online") 0.5f else 0.2f)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(device.color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(device.icon, null, tint = device.color, modifier = Modifier.size(26.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(device.name, style = MaterialTheme.typography.labelLarge, color = SkylineColors.TextPrimary)
                Text(device.type, style = MaterialTheme.typography.labelSmall, color = SkylineColors.TextMuted)
                Text(device.ip, style = MaterialTheme.typography.bodySmall, color = SkylineColors.CyberCyan)
            }
            Column(horizontalAlignment = Alignment.End) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(device.color.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        device.status,
                        style = MaterialTheme.typography.labelSmall,
                        color = device.color
                    )
                }
            }
        }
    }
}
