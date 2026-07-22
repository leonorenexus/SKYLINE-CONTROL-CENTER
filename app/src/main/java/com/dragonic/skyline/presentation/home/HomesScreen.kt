package com.dragonic.skyline.presentation.home

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dragonic.skyline.core.navigation.Screen
import com.dragonic.skyline.core.theme.SkylineColors
import com.dragonic.skyline.core.theme.skyline
import com.dragonic.skyline.presentation.components.*

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val bannerPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { viewModel.onBannerSelected(it) } }

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = { SkylineBottomNavBar(navController) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Background layers
            if (uiState.bgVideoUri != null) {
                VideoBackground(uiState.bgVideoUri, Modifier.fillMaxSize())
            }
            NebulaBackground(Modifier.fillMaxSize())
            CyberGridBackground(Modifier.fillMaxSize())
            SakuraParticleOverlay(Modifier.fillMaxSize(), count = 20)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SkylineColors.DeepVoid.copy(alpha = 0.5f))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                // ── HEADER ──
                HomeHeader(
                    username = uiState.username,
                    onNotification = { Toast.makeText(context, "Notifikasi", Toast.LENGTH_SHORT).show() },
                    onSettings = { navController.navigate(Screen.Settings.route) }
                )

                Spacer(Modifier.height(8.dp))

                // ── CENTER REACTOR ──
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ReactorLogo(size = 260.dp)
                }

                Spacer(Modifier.height(8.dp))

                // ── USER CARD + CLOCK ──
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    UserCard(
                        username = uiState.username,
                        status   = "Online",
                        modifier = Modifier.weight(1f)
                    )
                    RealtimeClockWidget(modifier = Modifier.weight(1f))
                }

                Spacer(Modifier.height(12.dp))

                // ── HERO BANNER ──
                HeroBannerSection(
                    bannerUri = uiState.bannerUri,
                    onChangeBanner = { bannerPicker.launch("*/*") },
                    onRemoveBanner = viewModel::onBannerRemoved,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(Modifier.height(12.dp))

                // ── STATUS CARDS ──
                Text(
                    "STATUS SISTEM",
                    style    = MaterialTheme.typography.labelLarge,
                    color    = SkylineColors.TextMuted,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(8.dp))
                StatusCardsGrid(
                    uiState  = uiState,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

// ── HEADER ──────────────────────────────────────────────────────

@Composable
private fun HomeHeader(
    username: String,
    onNotification: () -> Unit,
    onSettings: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo text
        Column {
            Text(
                "SKYLINE",
                style = MaterialTheme.typography.headlineMedium,
                color = SkylineColors.SakuraPink
            )
            Text(
                "Selamat datang, $username",
                style = MaterialTheme.typography.bodySmall,
                color = SkylineColors.TextSecondary
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(
                onClick = onNotification,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SkylineColors.GlassWhite)
            ) {
                Icon(Icons.Rounded.Notifications, null, tint = SkylineColors.SakuraPink)
            }
            IconButton(
                onClick = onSettings,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SkylineColors.GlassWhite)
            ) {
                Icon(Icons.Rounded.Settings, null, tint = SkylineColors.CyberCyan)
            }
        }
    }
}

// ── USER CARD ───────────────────────────────────────────────────

@Composable
fun UserCard(username: String, status: String, modifier: Modifier = Modifier) {
    GlassCard(modifier = modifier, animated = true) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(SkylineColors.SakuraPink, SkylineColors.MidPlum)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text  = username.firstOrNull()?.uppercaseChar()?.toString() ?: "L",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                // Online dot
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(SkylineColors.StatusOnline)
                        .align(Alignment.BottomEnd)
                )
            }
            Text(
                username,
                style = MaterialTheme.typography.labelLarge,
                color = SkylineColors.TextPrimary,
                maxLines = 1
            )
            Text(
                "⚡ DRAGONIC PRO",
                style = MaterialTheme.typography.labelSmall,
                color = SkylineColors.SakuraPink
            )
            TextButton(
                onClick  = {},
                modifier = Modifier.height(28.dp)
            ) {
                Text(
                    "Lihat Profil",
                    style = MaterialTheme.typography.labelSmall,
                    color = SkylineColors.CyberCyan
                )
            }
        }
    }
}

// ── HERO BANNER ─────────────────────────────────────────────────

@Composable
fun HeroBannerSection(
    bannerUri: Uri?,
    onChangeBanner: () -> Unit,
    onRemoveBanner: () -> Unit,
    modifier: Modifier = Modifier
) {
    GlassCard(
        modifier     = modifier.fillMaxWidth().height(180.dp),
        cornerRadius = 16.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (bannerUri != null) {
                AsyncImage(
                    model               = bannerUri,
                    contentDescription  = null,
                    contentScale        = ContentScale.Crop,
                    modifier            = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(SkylineColors.DeepVoid.copy(alpha = 0.3f))
                )
            } else {
                // Default futuristic placeholder
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    SkylineColors.MidPlum.copy(alpha = 0.6f),
                                    SkylineColors.DeepPurple.copy(alpha = 0.8f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Rounded.Upload,
                            contentDescription = null,
                            tint   = SkylineColors.SakuraPink.copy(alpha = 0.7f),
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "TAP UNTUK UPLOAD BANNER",
                            style = MaterialTheme.typography.labelMedium,
                            color = SkylineColors.TextMuted
                        )
                        Text(
                            "MP4 · JPG · PNG · GIF · WEBP",
                            style = MaterialTheme.typography.labelSmall,
                            color = SkylineColors.TextMuted.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            // Neon border
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        1.dp,
                        Brush.linearGradient(
                            listOf(SkylineColors.GlassBorder, SkylineColors.GlassBorderCyan)
                        ),
                        RoundedCornerShape(16.dp)
                    )
            )

            // Control buttons
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (bannerUri != null) {
                    SmallIconButton(Icons.Rounded.Delete, SkylineColors.StatusOffline, onRemoveBanner)
                }
                SmallIconButton(Icons.Rounded.Upload, SkylineColors.SakuraPink, onChangeBanner)
            }

            // Tap to upload (whole area)
            if (bannerUri == null) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .clickable(onClick = onChangeBanner))
            }
        }
    }
}

@Composable
private fun SmallIconButton(icon: ImageVector, tint: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(SkylineColors.DeepVoid.copy(alpha = 0.7f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, null, tint = tint, modifier = Modifier.size(16.dp))
    }
}

// ── STATUS CARDS ────────────────────────────────────────────────

@Composable
fun StatusCardsGrid(uiState: HomeUiState, modifier: Modifier = Modifier) {
    val cards = listOf(
        StatusCardData("Device", "${uiState.deviceCount}", Icons.Rounded.Devices,     SkylineColors.SakuraPink,    "ONLINE"),
        StatusCardData("Internet","Connected", Icons.Rounded.Language,               SkylineColors.StatusOnline,  "OK"),
        StatusCardData("WiFi",   uiState.ssid, Icons.Rounded.Wifi,                   SkylineColors.CyberCyan,     ""),
        StatusCardData("Speed",  "${uiState.downloadMbps} Mbps", Icons.Rounded.Speed,SkylineColors.NeonViolet,    "↓"),
        StatusCardData("Ping",   "${uiState.pingMs} ms",  Icons.Rounded.NetworkCheck, SkylineColors.StatusWarning, ""),
        StatusCardData("Aktivitas","${uiState.activityCount}", Icons.Rounded.Timeline,SkylineColors.SakuraPinkLight,"LOG")
    )

    LazyVerticalGrid(
        columns              = GridCells.Fixed(2),
        modifier             = modifier.heightIn(max = 400.dp),
        contentPadding       = PaddingValues(0.dp),
        horizontalArrangement= Arrangement.spacedBy(10.dp),
        verticalArrangement  = Arrangement.spacedBy(10.dp),
        userScrollEnabled     = false
    ) {
        items(cards) { card ->
            StatusCard(card)
        }
    }
}

data class StatusCardData(
    val title: String,
    val value: String,
    val icon: ImageVector,
    val color: Color,
    val badge: String
)

@Composable
private fun StatusCard(data: StatusCardData) {
    val inf = rememberInfiniteTransition(label = "card")
    val glow by inf.animateFloat(0.4f, 0.9f,
        infiniteRepeatable(tween(2000, easing = EaseInOutSine), RepeatMode.Reverse), "card_glow")

    GlassCard(
        modifier   = Modifier.fillMaxWidth(),
        glowColor  = data.color.copy(alpha = glow * 0.4f),
        borderColor= data.color.copy(alpha = 0.4f)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(data.color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(data.icon, null, tint = data.color, modifier = Modifier.size(22.dp))
            }
            Column {
                Text(
                    data.title,
                    style = MaterialTheme.typography.labelSmall,
                    color = SkylineColors.TextMuted
                )
                Text(
                    data.value,
                    style    = MaterialTheme.typography.labelLarge,
                    color    = data.color,
                    maxLines = 1
                )
                if (data.badge.isNotEmpty()) {
                    Text(
                        data.badge,
                        style = MaterialTheme.typography.labelSmall,
                        color = data.color.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}
