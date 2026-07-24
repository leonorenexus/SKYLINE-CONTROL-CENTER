package com.dragonic.skyline.presentation.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.dragonic.skyline.core.theme.SkylineColors
import com.dragonic.skyline.presentation.components.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinish: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "splash")

    // Entry animations
    var entered by remember { mutableStateOf(false) }
    val alpha   by animateFloatAsState(
        targetValue   = if (entered) 1f else 0f,
        animationSpec = tween(800, easing = EaseOut),
        label         = "splash_alpha"
    )
    val scale by animateFloatAsState(
        targetValue   = if (entered) 1f else 0.7f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium),
        label         = "splash_scale"
    )

    // Glow pulse
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue  = 0.4f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1500, easing = EaseInOutSine), RepeatMode.Reverse),
        label         = "glow"
    )

    LaunchedEffect(Unit) {
        entered = true
        delay(3000)
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    listOf(
                        SkylineColors.MidPlum,
                        SkylineColors.DeepPurple,
                        SkylineColors.DeepVoid
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Nebula BG
        NebulaBackground(Modifier.fillMaxSize())

        // Sakura particles
        SakuraParticleOverlay(Modifier.fillMaxSize(), count = 20)

        // Cyber grid
        CyberGridBackground(Modifier.fillMaxSize())

        // Dark overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SkylineColors.DeepVoid.copy(alpha = 0.4f))
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier            = Modifier
                .alpha(alpha)
                .scale(scale)
        ) {
            // Reactor logo
            ReactorLogo(size = 220.dp)

            Spacer(Modifier.height(24.dp))

            // App name
            Text(
                text  = "SKYLINE",
                style = MaterialTheme.typography.displayMedium,
                color = SkylineColors.SakuraPink
            )
            Text(
                text  = "CONTROL CENTER",
                style = MaterialTheme.typography.headlineSmall,
                color = SkylineColors.CyberCyan
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text  = "DRAGONIC // LEONORE TECH",
                style = MaterialTheme.typography.labelMedium,
                color = SkylineColors.TextMuted
            )

            Spacer(Modifier.height(48.dp))

            // Loading dots
            LoadingDots()
        }
    }
}

@Composable
private fun LoadingDots() {
    val infiniteTransition = rememberInfiniteTransition(label = "dots")
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(3) { i ->
            val alpha by infiniteTransition.animateFloat(
                initialValue  = 0.2f, targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    tween(600, delayMillis = i * 200, easing = EaseInOutSine),
                    RepeatMode.Reverse
                ),
                label = "dot_$i"
            )
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .alpha(alpha)
                    .background(
                        SkylineColors.SakuraPink,
                        androidx.compose.foundation.shape.CircleShape
                    )
            )
        }
    }
}
