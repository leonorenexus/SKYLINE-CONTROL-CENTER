package com.dragonic.skyline.presentation.analytics

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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dragonic.skyline.core.theme.SkylineColors
import com.dragonic.skyline.presentation.components.*
import kotlin.math.sin

@Composable
fun AnalyticsScreen(navController: NavController) {
    val inf = rememberInfiniteTransition(label = "analytics")
    val tick by inf.animateFloat(0f, 100f,
        infiniteRepeatable(tween(5000, easing = LinearEasing)), "tick")

    // Simulated chart data
    val dailyData  = remember { listOf(45f, 67f, 82f, 58f, 91f, 74f, 88f) }
    val weeklyData = remember { listOf(320f, 440f, 380f, 510f, 490f, 620f, 580f, 710f) }

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
                Text("ANALYTICS", style = MaterialTheme.typography.headlineMedium, color = SkylineColors.SakuraPink)
                Text("Statistik dan penggunaan realtime", style = MaterialTheme.typography.bodySmall, color = SkylineColors.TextMuted)

                // Summary metrics
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MetricCard("Uptime", "99.8%",    Icons.Rounded.CheckCircle,   SkylineColors.StatusOnline,  Modifier.weight(1f))
                    MetricCard("Errors",  "3",        Icons.Rounded.Error,          SkylineColors.StatusOffline, Modifier.weight(1f))
                    MetricCard("Events",  "1,284",    Icons.Rounded.Timeline,       SkylineColors.CyberCyan,     Modifier.weight(1f))
                }

                // Daily Usage Chart
                GlassCard(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("PENGGUNAAN HARIAN", style = MaterialTheme.typography.labelLarge, color = SkylineColors.SakuraPink)
                        Spacer(Modifier.height(4.dp))
                        Text("7 hari terakhir", style = MaterialTheme.typography.bodySmall, color = SkylineColors.TextMuted)
                        Spacer(Modifier.height(12.dp))
                        BarChart(
                            values  = dailyData,
                            labels  = listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min"),
                            color   = SkylineColors.SakuraPink,
                            modifier= Modifier.fillMaxWidth().height(140.dp)
                        )
                    }
                }

                // Network Statistics
                GlassCard(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("STATISTIK JARINGAN", style = MaterialTheme.typography.labelLarge, color = SkylineColors.CyberCyan)
                        Spacer(Modifier.height(12.dp))
                        SineWaveChart(
                            tick     = tick,
                            color1   = SkylineColors.CyberCyan,
                            color2   = SkylineColors.SakuraPink,
                            modifier = Modifier.fillMaxWidth().height(100.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            LegendItem("Download", SkylineColors.CyberCyan)
                            LegendItem("Upload",   SkylineColors.SakuraPink)
                        }
                    }
                }

                // Activity Timeline
                GlassCard(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("AKTIVITAS TERBARU", style = MaterialTheme.typography.labelLarge, color = SkylineColors.NeonViolet)
                        Spacer(Modifier.height(8.dp))
                        timelineItems.forEach { item ->
                            TimelineEntry(item)
                        }
                    }
                }

                // Device Statistics
                GlassCard(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("STATISTIK PERANGKAT", style = MaterialTheme.typography.labelLarge, color = SkylineColors.StatusWarning)
                        Spacer(Modifier.height(12.dp))
                        deviceStats.forEach { (name, pct) ->
                            DeviceStatBar(name, pct)
                            Spacer(Modifier.height(6.dp))
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun MetricCard(
    label: String, value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color, modifier: Modifier
) {
    GlassCard(modifier = modifier, borderColor = color.copy(alpha = 0.4f)) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, null, tint = color, modifier = Modifier.size(20.dp))
            Spacer(Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.labelLarge, color = color)
            Text(label, style = MaterialTheme.typography.labelSmall, color = SkylineColors.TextMuted)
        }
    }
}

@Composable
private fun BarChart(
    values: List<Float>,
    labels: List<String>,
    color: Color,
    modifier: Modifier = Modifier
) {
    val maxVal = values.maxOrNull() ?: 1f
    val animProgress by animateFloatAsState(1f,
        tween(1000, easing = EaseOut), label = "bar_anim")

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            values.forEach { v ->
                val frac = (v / maxVal) * animProgress
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 3.dp)
                        .fillMaxHeight(frac)
                        .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                        .background(
                            Brush.verticalGradient(listOf(color, color.copy(alpha = 0.3f)))
                        )
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            labels.forEach { l ->
                Text(l, style = MaterialTheme.typography.labelSmall,
                    color = SkylineColors.TextMuted,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            }
        }
    }
}

@Composable
private fun SineWaveChart(
    tick: Float, color1: Color, color2: Color, modifier: Modifier = Modifier
) {
    androidx.compose.foundation.Canvas(modifier = modifier) {
        val pts1 = mutableListOf<Offset>()
        val pts2 = mutableListOf<Offset>()
        val steps = 200
        for (i in 0..steps) {
            val x = i / steps.toFloat() * size.width
            val y1 = size.height / 2f + sin((i / 10f) + tick * 0.1f) * size.height * 0.35f
            val y2 = size.height / 2f + sin((i / 10f) + tick * 0.1f + 2f) * size.height * 0.25f
            pts1 += Offset(x, y1)
            pts2 += Offset(x, y2)
        }
        val path1 = Path().apply { pts1.forEachIndexed { i, p -> if (i == 0) moveTo(p.x, p.y) else lineTo(p.x, p.y) } }
        val path2 = Path().apply { pts2.forEachIndexed { i, p -> if (i == 0) moveTo(p.x, p.y) else lineTo(p.x, p.y) } }
        drawPath(path1, color1.copy(alpha = 0.8f), style = Stroke(2f))
        drawPath(path2, color2.copy(alpha = 0.8f), style = Stroke(2f))
    }
}

@Composable
private fun LegendItem(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Box(Modifier.size(10.dp).clip(RoundedCornerShape(2.dp)).background(color))
        Text(label, style = MaterialTheme.typography.labelSmall, color = SkylineColors.TextMuted)
    }
}

data class TimelineItem(val time: String, val event: String, val color: Color)

val timelineItems = listOf(
    TimelineItem("10:42", "Login berhasil", SkylineColors.StatusOnline),
    TimelineItem("10:38", "WiFi reconnected", SkylineColors.CyberCyan),
    TimelineItem("10:30", "Smart Lamp 1 → ON", SkylineColors.StatusWarning),
    TimelineItem("10:15", "Sensor suhu: 28.5°C", SkylineColors.StatusInfo),
    TimelineItem("09:55", "Smart Plug A → ON", SkylineColors.SakuraPink)
)

@Composable
private fun TimelineEntry(item: TimelineItem) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(8.dp).clip(RoundedCornerShape(2.dp)).background(item.color))
        Text(item.time, style = MaterialTheme.typography.labelSmall, color = SkylineColors.TextMuted, modifier = Modifier.width(40.dp))
        Text(item.event, style = MaterialTheme.typography.bodySmall, color = SkylineColors.TextPrimary)
    }
}

val deviceStats = listOf(
    "Smart Lamp" to 0.75f,
    "Smart Plug" to 0.50f,
    "Sensor" to 0.90f,
    "Relay" to 0.30f
)

@Composable
private fun DeviceStatBar(name: String, fraction: Float) {
    val anim by animateFloatAsState(fraction, tween(1000, easing = EaseOut), label = "stat_$name")
    Column {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(name, style = MaterialTheme.typography.bodySmall, color = SkylineColors.TextSecondary)
            Text("${(fraction * 100).toInt()}%", style = MaterialTheme.typography.labelSmall, color = SkylineColors.SakuraPink)
        }
        Spacer(Modifier.height(2.dp))
        Box(
            modifier = Modifier.fillMaxWidth().height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(SkylineColors.GlassWhite)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(anim).fillMaxHeight()
                    .clip(RoundedCornerShape(3.dp))
                    .background(
                        Brush.horizontalGradient(listOf(SkylineColors.SakuraPink, SkylineColors.CyberCyan))
                    )
            )
        }
    }
}
