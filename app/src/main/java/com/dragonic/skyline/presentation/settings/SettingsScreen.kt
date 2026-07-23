package com.dragonic.skyline.presentation.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dragonic.skyline.core.theme.SkylineColors
import com.dragonic.skyline.presentation.components.*

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val bgPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let { uri -> viewModel.onBgSelected(uri) }
    }
    val bannerPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let { uri -> viewModel.onBannerSelected(uri) }
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
                Text("PENGATURAN", style = MaterialTheme.typography.headlineMedium, color = SkylineColors.SakuraPink)
                Text("Kustomisasi pengalaman Skyline", style = MaterialTheme.typography.bodySmall, color = SkylineColors.TextMuted)

                // ── GENERAL ──
                SettingsSection("UMUM", Icons.Rounded.Tune) {
                    SettingsSwitchRow(
                        icon   = Icons.Rounded.DarkMode,
                        label  = "Mode Gelap",
                        desc   = "Aktifkan tema gelap",
                        color  = SkylineColors.NeonViolet,
                        checked = uiState.darkMode,
                        onToggle = viewModel::onDarkModeToggle
                    )
                    SettingsSwitchRow(
                        icon   = Icons.Rounded.Animation,
                        label  = "Animasi Premium",
                        desc   = "Aktifkan efek animasi penuh",
                        color  = SkylineColors.SakuraPink,
                        checked = uiState.animationEnabled,
                        onToggle = viewModel::onAnimationToggle
                    )
                }

                // ── VISUAL ──
                SettingsSection("VISUAL & TEMA", Icons.Rounded.Palette) {
                    SettingsClickRow(
                        icon  = Icons.Rounded.VideoFile,
                        label = "Upload Background",
                        desc  = "MP4 · JPG · PNG · GIF · WEBP",
                        color = SkylineColors.CyberCyan,
                        onClick = { bgPicker.launch("*/*") }
                    )
                    if (uiState.bgUri != null) {
                        SettingsClickRow(
                            icon  = Icons.Rounded.Delete,
                            label = "Hapus Background",
                            desc  = "Kembali ke nebula default",
                            color = SkylineColors.StatusOffline,
                            onClick = viewModel::onBgRemoved
                        )
                    }
                    SettingsClickRow(
                        icon  = Icons.Rounded.Image,
                        label = "Upload Banner",
                        desc  = "Banner hero di halaman utama",
                        color = SkylineColors.SakuraPink,
                        onClick = { bannerPicker.launch("*/*") }
                    )
                    if (uiState.bannerUri != null) {
                        SettingsClickRow(
                            icon  = Icons.Rounded.DeleteForever,
                            label = "Hapus Banner",
                            desc  = "Reset ke placeholder default",
                            color = SkylineColors.StatusOffline,
                            onClick = viewModel::onBannerRemoved
                        )
                    }
                }

                // ── NOTIFICATION ──
                SettingsSection("NOTIFIKASI", Icons.Rounded.Notifications) {
                    SettingsSwitchRow(
                        icon   = Icons.Rounded.NotificationsActive,
                        label  = "Push Notification",
                        desc   = "Terima notifikasi realtime",
                        color  = SkylineColors.StatusInfo,
                        checked = uiState.pushNotification,
                        onToggle = viewModel::onPushNotifToggle
                    )
                    SettingsSwitchRow(
                        icon   = Icons.Rounded.VolumeUp,
                        label  = "Suara",
                        desc   = "Aktifkan suara notifikasi",
                        color  = SkylineColors.StatusWarning,
                        checked = uiState.soundEnabled,
                        onToggle = viewModel::onSoundToggle
                    )
                    SettingsSwitchRow(
                        icon   = Icons.Rounded.Vibration,
                        label  = "Getar",
                        desc   = "Aktifkan getaran notifikasi",
                        color  = SkylineColors.NeonViolet,
                        checked = uiState.vibrationEnabled,
                        onToggle = viewModel::onVibrationToggle
                    )
                }

                // ── SECURITY ──
                SettingsSection("KEAMANAN", Icons.Rounded.Security) {
                    SettingsSwitchRow(
                        icon   = Icons.Rounded.Fingerprint,
                        label  = "Fingerprint / Biometrik",
                        desc   = "Login menggunakan biometrik",
                        color  = SkylineColors.StatusOnline,
                        checked = uiState.biometricEnabled,
                        onToggle = viewModel::onBiometricToggle
                    )
                    SettingsSwitchRow(
                        icon   = Icons.Rounded.Pin,
                        label  = "PIN Lock",
                        desc   = "Kunci aplikasi dengan PIN",
                        color  = SkylineColors.SakuraPink,
                        checked = uiState.pinEnabled,
                        onToggle = viewModel::onPinToggle
                    )
                    SettingsClickRow(
                        icon  = Icons.Rounded.VpnKey,
                        label = "Ganti PIN",
                        desc  = "Ubah kode PIN keamanan",
                        color = SkylineColors.CyberCyan,
                        onClick = {}
                    )
                }

                // ── ABOUT ──
                SettingsSection("TENTANG", Icons.Rounded.Info) {
                    SettingsInfoRow("Versi Aplikasi", "1.0.0 (Build 1)")
                    SettingsInfoRow("Brand", "DRAGONIC")
                    SettingsInfoRow("Developer", "Leonore Tech Team")
                    SettingsInfoRow("Website", "leonore.web.id")
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    GlassCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(icon, null, tint = SkylineColors.SakuraPink, modifier = Modifier.size(18.dp))
                Text(title, style = MaterialTheme.typography.labelLarge, color = SkylineColors.SakuraPink)
            }
            Spacer(Modifier.height(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                content()
            }
        }
    }
}

@Composable
private fun SettingsSwitchRow(
    icon: ImageVector,
    label: String,
    desc: String,
    color: Color,
    checked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) { Icon(icon, null, tint = color, modifier = Modifier.size(18.dp)) }
        Column(modifier = Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.bodyMedium, color = SkylineColors.TextPrimary)
            Text(desc, style = MaterialTheme.typography.labelSmall, color = SkylineColors.TextMuted)
        }
        Switch(
            checked = checked, onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor   = Color.White,
                checkedTrackColor   = SkylineColors.SakuraPink,
                uncheckedTrackColor = SkylineColors.GlassWhite
            )
        )
    }
}

@Composable
private fun SettingsClickRow(
    icon: ImageVector,
    label: String,
    desc: String,
    color: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) { Icon(icon, null, tint = color, modifier = Modifier.size(18.dp)) }
        Column(modifier = Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.bodyMedium, color = SkylineColors.TextPrimary)
            Text(desc, style = MaterialTheme.typography.labelSmall, color = SkylineColors.TextMuted)
        }
        Icon(Icons.Rounded.ChevronRight, null, tint = SkylineColors.TextMuted, modifier = Modifier.size(16.dp))
    }
}

@Composable
private fun SettingsInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodySmall, color = SkylineColors.TextMuted)
        Text(value, style = MaterialTheme.typography.labelMedium, color = SkylineColors.TextPrimary)
    }
}
