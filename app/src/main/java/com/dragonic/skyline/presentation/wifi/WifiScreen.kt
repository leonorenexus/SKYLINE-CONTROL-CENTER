package com.dragonic.skyline.presentation.wifi

import androidx.compose.animation.core.*
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dragonic.skyline.core.theme.SkylineColors
import com.dragonic.skyline.presentation.components.*

@Composable
fun WifiScreen(
    navController: NavController,
    viewModel: WifiViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Spacer(Modifier.height(8.dp))

                Text(
                    "WIFI MONITOR",
                    style = MaterialTheme.typography.headlineMedium,
                    color = SkylineColors.SakuraPink
                )
                Text(
                    "Realtime network diagnostics",
                    style = MaterialTheme.typography.bodySmall,
                    color = SkylineColors.TextMuted
                )

                // Connection Info Card
                GlassCard(Modifier.fillMaxWidth(), animated = true) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("INFORMASI KONEKSI", style = MaterialTheme.typography.labelLarge, color = SkylineColors.CyberCyan)
                        WifiInfoRow(Icons.Rounded.Wifi, "SSID", uiState.ssid)
                        WifiInfoRow(Icons.Rounded.Router, "BSSID", uiState.bssid)
                        WifiInfoRow(Icons.Rounded.Computer, "Local IP", uiState.localIp)
                        WifiInfoRow(Icons.Rounded.Hub, "Gateway", uiState.gateway)
                        WifiInfoRow(Icons.Rounded.Dns, "DNS", uiState.dns)
                        WifiInfoRow(Icons.Rounded.SignalWifi4Bar, "Signal", "${uiState.signalStrength} dBm")
                        WifiInfoRow(Icons.Rounded.Language, "Internet", if (uiState.internetConnected) "✓ Terhubung" else "✗ Tidak ada")
                    }
                }

                // Speed Monitor
                GlassCard(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("KECEPATAN REALTIME", style = MaterialTheme.typography.labelLarge, color = SkylineColors.SakuraPink)
                        Spacer(Modifier.height(12.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            SpeedGauge("↓ Download", uiState.downloadMbps, SkylineColors.CyberCyan)
                            SpeedGauge("↑ Upload", uiState.uploadMbps, SkylineColors.SakuraPink)
                            SpeedGauge("⟳ Ping", uiState.pingMs, SkylineColors.StatusWarning, "ms")
                        }
                    }
                }

                // Speed Chart
                GlassCard(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("GRAFIK KECEPATAN", style = MaterialTheme.typography.labelLarge, color = SkylineColors.SakuraPink)
                        Spacer(Modifier.height(8.dp))
                        SimpleSpeedChart(
                            downloadHistory = uiState.downloadHistory,
                            uploadHistory   = uiState.uploadHistory,
                            modifier        = Modifier.fillMaxWidth().height(120.dp)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun WifiInfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, null, tint = SkylineColors.SakuraPink, modifier = Modifier.size(16.dp))
            Text(label, style = MaterialTheme.typography.bodySmall, color = SkylineColors.TextMuted)
        }
        Text(value, style = MaterialTheme.typography.labelMedium, color = SkylineColors.TextPrimary)
    }
    Divider(color = SkylineColors.GlassBorder.copy(alpha = 0.3f), thickness = 0.5.dp)
}

@Composable
private fun SpeedGauge(label: String, value: Int, color: Color, unit: String = "Mbps") {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val inf = rememberInfiniteTransition(label = "gauge")
        val glow by inf.animateFloat(0.5f, 1f,
            infiniteRepeatable(tween(1500, easing = EaseInOutSine), RepeatMode.Reverse), "g")

        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(color.copy(alpha = 0.1f * glow))
                .border(1.dp, color.copy(alpha = glow), RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("$value", style = MaterialTheme.typography.headlineSmall, color = color)
                Text(unit, style = MaterialTheme.typography.labelSmall, color = SkylineColors.TextMuted)
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(label, style = MaterialTheme.typography.labelSmall, color = SkylineColors.TextMuted)
    }
}

@Composable
private fun SimpleSpeedChart(
    downloadHistory: List<Float>,
    uploadHistory: List<Float>,
    modifier: Modifier = Modifier
) {
    androidx.compose.foundation.Canvas(modifier = modifier) {
        if (downloadHistory.isEmpty()) return@Canvas
        val maxVal = (downloadHistory + uploadHistory).maxOrNull()?.coerceAtLeast(1f) ?: 1f
        val stepX  = size.width / (downloadHistory.size - 1).coerceAtLeast(1)

        // Download line
        val dPath = androidx.compose.ui.graphics.Path()
        downloadHistory.forEachIndexed { i, v ->
            val x = i * stepX
            val y = size.height - (v / maxVal) * size.height
            if (i == 0) dPath.moveTo(x, y) else dPath.lineTo(x, y)
        }
        drawPath(dPath, SkylineColors.CyberCyan, style = androidx.compose.ui.graphics.drawscope.Stroke(2f))

        // Upload line
        val uPath = androidx.compose.ui.graphics.Path()
        uploadHistory.forEachIndexed { i, v ->
            val x = i * stepX
            val y = size.height - (v / maxVal) * size.height
            if (i == 0) uPath.moveTo(x, y) else uPath.lineTo(x, y)
        }
        drawPath(uPath, SkylineColors.SakuraPink, style = androidx.compose.ui.graphics.drawscope.Stroke(2f))
    }
}
