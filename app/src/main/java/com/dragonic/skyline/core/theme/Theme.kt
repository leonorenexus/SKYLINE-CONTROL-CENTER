package com.dragonic.skyline.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ──────────────────────────────────────────────────────────────
// SKYLINE SAKURA CYBER PALETTE
// ──────────────────────────────────────────────────────────────

object SkylineColors {
    // Primary Sakura Pink
    val SakuraPink        = Color(0xFFFF4DB8)
    val SakuraPinkLight   = Color(0xFFFF66CC)
    val SakuraPinkPale    = Color(0xFFFF99DD)
    val SakuraPinkGlow    = Color(0x66FF4DB8)
    val SakuraPinkFaint   = Color(0x22FF4DB8)

    // Background Deep Space
    val DeepVoid          = Color(0xFF0A0810)
    val DeepPurple        = Color(0xFF0F0A17)
    val DarkPlum          = Color(0xFF1A1026)
    val MidPlum           = Color(0xFF2B143D)
    val SurfacePurple     = Color(0xFF1E1030)

    // Accent / Secondary
    val CyberCyan         = Color(0xFF00F5FF)
    val CyberCyanGlow     = Color(0x4400F5FF)
    val NeonViolet        = Color(0xFFBF5FFF)
    val NeonVioletGlow    = Color(0x44BF5FFF)
    val HoloPink          = Color(0xFFFF1493)

    // Glass
    val GlassWhite        = Color(0x14FFFFFF)
    val GlassWhiteMid     = Color(0x22FFFFFF)
    val GlassBorder       = Color(0x33FF4DB8)
    val GlassBorderCyan   = Color(0x3300F5FF)

    // Text
    val TextPrimary       = Color(0xFFF0E8FF)
    val TextSecondary     = Color(0xFFB89FD4)
    val TextMuted         = Color(0xFF6E5A8A)
    val TextOnAccent      = Color(0xFF0F0A17)

    // Status
    val StatusOnline      = Color(0xFF00FF9F)
    val StatusOffline     = Color(0xFFFF3366)
    val StatusWarning     = Color(0xFFFFCC00)
    val StatusInfo        = Color(0xFF00C8FF)
}

private val SkylineDarkScheme = darkColorScheme(
    primary            = SkylineColors.SakuraPink,
    onPrimary          = SkylineColors.TextOnAccent,
    primaryContainer   = SkylineColors.MidPlum,
    onPrimaryContainer = SkylineColors.SakuraPinkPale,
    secondary          = SkylineColors.CyberCyan,
    onSecondary        = SkylineColors.DeepVoid,
    secondaryContainer = SkylineColors.SurfacePurple,
    onSecondaryContainer = SkylineColors.CyberCyan,
    tertiary           = SkylineColors.NeonViolet,
    onTertiary         = SkylineColors.TextOnAccent,
    background         = SkylineColors.DeepVoid,
    onBackground       = SkylineColors.TextPrimary,
    surface            = SkylineColors.DeepPurple,
    onSurface          = SkylineColors.TextPrimary,
    surfaceVariant     = SkylineColors.DarkPlum,
    onSurfaceVariant   = SkylineColors.TextSecondary,
    outline            = SkylineColors.GlassBorder,
    outlineVariant     = SkylineColors.GlassBorderCyan,
    error              = SkylineColors.StatusOffline,
    onError            = SkylineColors.TextPrimary
)

data class SkylineExtendedColors(
    val glassWhite: Color,
    val glassWhiteMid: Color,
    val glassBorder: Color,
    val glassBorderCyan: Color,
    val sakuraPinkGlow: Color,
    val sakuraPinkFaint: Color,
    val cyberCyanGlow: Color,
    val neonVioletGlow: Color,
    val statusOnline: Color,
    val statusOffline: Color,
    val statusWarning: Color,
    val statusInfo: Color,
    val holoPink: Color,
    val textMuted: Color,
    val deepVoid: Color
)

val LocalSkylineColors = staticCompositionLocalOf {
    SkylineExtendedColors(
        glassWhite     = SkylineColors.GlassWhite,
        glassWhiteMid  = SkylineColors.GlassWhiteMid,
        glassBorder    = SkylineColors.GlassBorder,
        glassBorderCyan= SkylineColors.GlassBorderCyan,
        sakuraPinkGlow = SkylineColors.SakuraPinkGlow,
        sakuraPinkFaint= SkylineColors.SakuraPinkFaint,
        cyberCyanGlow  = SkylineColors.CyberCyanGlow,
        neonVioletGlow = SkylineColors.NeonVioletGlow,
        statusOnline   = SkylineColors.StatusOnline,
        statusOffline  = SkylineColors.StatusOffline,
        statusWarning  = SkylineColors.StatusWarning,
        statusInfo     = SkylineColors.StatusInfo,
        holoPink       = SkylineColors.HoloPink,
        textMuted      = SkylineColors.TextMuted,
        deepVoid       = SkylineColors.DeepVoid
    )
}

@Composable
fun SkylineTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalSkylineColors provides SkylineExtendedColors(
        glassWhite     = SkylineColors.GlassWhite,
        glassWhiteMid  = SkylineColors.GlassWhiteMid,
        glassBorder    = SkylineColors.GlassBorder,
        glassBorderCyan= SkylineColors.GlassBorderCyan,
        sakuraPinkGlow = SkylineColors.SakuraPinkGlow,
        sakuraPinkFaint= SkylineColors.SakuraPinkFaint,
        cyberCyanGlow  = SkylineColors.CyberCyanGlow,
        neonVioletGlow = SkylineColors.NeonVioletGlow,
        statusOnline   = SkylineColors.StatusOnline,
        statusOffline  = SkylineColors.StatusOffline,
        statusWarning  = SkylineColors.StatusWarning,
        statusInfo     = SkylineColors.StatusInfo,
        holoPink       = SkylineColors.HoloPink,
        textMuted      = SkylineColors.TextMuted,
        deepVoid       = SkylineColors.DeepVoid
    )) {
        MaterialTheme(
            colorScheme = SkylineDarkScheme,
            typography  = SkylineTypography,
            content     = content
        )
    }
}

val MaterialTheme.skyline: SkylineExtendedColors
    @Composable get() = LocalSkylineColors.current
