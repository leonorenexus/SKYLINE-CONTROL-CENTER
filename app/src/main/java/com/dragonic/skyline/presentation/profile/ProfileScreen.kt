package com.dragonic.skyline.presentation.profile

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dragonic.skyline.core.navigation.Screen
import com.dragonic.skyline.core.theme.SkylineColors
import com.dragonic.skyline.presentation.components.*

@Composable
fun ProfileScreen(navController: NavController) {
    val inf = rememberInfiniteTransition(label = "profile")
    val glow by inf.animateFloat(0.4f, 1f,
        infiniteRepeatable(tween(2000, easing = EaseInOutSine), RepeatMode.Reverse), "pg")

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = { SkylineBottomNavBar(navController) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            NebulaBackground(Modifier.fillMaxSize())
            CyberGridBackground(Modifier.fillMaxSize())
            SakuraParticleOverlay(Modifier.fillMaxSize(), count = 15)
            Box(Modifier.fillMaxSize().background(SkylineColors.DeepVoid.copy(alpha = 0.55f)))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(Modifier.height(8.dp))

                // ── AVATAR ──
                Box(contentAlignment = Alignment.Center) {
                    // Outer glow ring
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .drawBehind {
                                drawCircle(
                                    color     = SkylineColors.SakuraPink.copy(alpha = glow * 0.5f),
                                    radius    = size.minDimension * 0.6f,
                                    blendMode = BlendMode.Screen
                                )
                            }
                    )
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .border(2.dp,
                                Brush.sweepGradient(listOf(
                                    SkylineColors.SakuraPink,
                                    SkylineColors.CyberCyan,
                                    SkylineColors.NeonViolet,
                                    SkylineColors.SakuraPink
                                )), CircleShape)
                            .background(
                                Brush.radialGradient(listOf(
                                    SkylineColors.MidPlum, SkylineColors.DeepVoid
                                ))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "R",
                            style      = MaterialTheme.typography.displayMedium,
                            color      = Color.White,
                            fontWeight = FontWeight.Black
                        )
                    }
                    // Online badge
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(SkylineColors.StatusOnline)
                            .border(2.dp, SkylineColors.DeepVoid, CircleShape)
                            .align(Alignment.BottomEnd)
                    )
                }

                // ── NAME + BADGE ──
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Ren Leonore",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                    Spacer(Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(SkylineColors.SakuraPink.copy(alpha = 0.2f))
                            .border(1.dp, SkylineColors.SakuraPink.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                            .padding(horizontal = 14.dp, vertical = 4.dp)
                    ) {
                        Text(
                            "⚡ DRAGONIC PRO",
                            style = MaterialTheme.typography.labelLarge,
                            color = SkylineColors.SakuraPink
                        )
                    }
                }

                // ── STATS ROW ──
                GlassCard(Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ProfileStat("Perangkat", "5",    SkylineColors.CyberCyan)
                        VertDivider()
                        ProfileStat("Bergabung", "2024", SkylineColors.SakuraPink)
                        VertDivider()
                        ProfileStat("Aktivitas", "1.2K", SkylineColors.NeonViolet)
                    }
                }

                // ── INFO CARD ──
                GlassCard(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("INFORMASI AKUN", style = MaterialTheme.typography.labelLarge, color = SkylineColors.CyberCyan)
                        ProfileInfoRow(Icons.Rounded.Person,  "Username",    "ren_leonore")
                        ProfileInfoRow(Icons.Rounded.Email,   "Email",       "ren@leonore.web.id")
                        ProfileInfoRow(Icons.Rounded.Star,    "Membership",  "DRAGONIC PRO")
                        ProfileInfoRow(Icons.Rounded.CalendarToday, "Bergabung", "1 Jan 2024")
                        ProfileInfoRow(Icons.Rounded.Devices, "Perangkat",   "5 terdaftar")
                    }
                }

                // ── ACTION BUTTONS ──
                GlassCard(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        ProfileActionButton(Icons.Rounded.Edit,       "Edit Profil",      SkylineColors.SakuraPink) {}
                        ProfileActionButton(Icons.Rounded.Lock,       "Ganti Password",   SkylineColors.CyberCyan)  {}
                        ProfileActionButton(Icons.Rounded.Security,   "Keamanan Akun",    SkylineColors.NeonViolet) {}
                        HorizontalDivider(color = SkylineColors.GlassBorder.copy(alpha = 0.4f))
                        ProfileActionButton(Icons.Rounded.Logout,     "Keluar",           SkylineColors.StatusOffline) {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ProfileStat(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, style = MaterialTheme.typography.headlineSmall, color = color, fontWeight = FontWeight.Bold)
        Text(label, style = MaterialTheme.typography.labelSmall,   color = SkylineColors.TextMuted)
    }
}

@Composable
private fun VertDivider() {
    Box(
        modifier = Modifier
            .height(32.dp)
            .width(1.dp)
            .background(SkylineColors.GlassBorder)
    )
}

@Composable
private fun ProfileInfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(icon, null, tint = SkylineColors.SakuraPink, modifier = Modifier.size(16.dp))
            Text(label, style = MaterialTheme.typography.bodySmall, color = SkylineColors.TextMuted)
        }
        Text(value, style = MaterialTheme.typography.labelMedium, color = SkylineColors.TextPrimary)
    }
    HorizontalDivider(color = SkylineColors.GlassBorder.copy(alpha = 0.2f))
}

@Composable
private fun ProfileActionButton(
    icon: ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = color, modifier = Modifier.size(18.dp))
        }
        Text(label, style = MaterialTheme.typography.bodyMedium, color = SkylineColors.TextPrimary, modifier = Modifier.weight(1f))
        Icon(Icons.Rounded.ChevronRight, null, tint = SkylineColors.TextMuted, modifier = Modifier.size(16.dp))
    }
}
