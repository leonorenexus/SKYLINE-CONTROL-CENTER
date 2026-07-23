package com.dragonic.skyline.presentation.login

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dragonic.skyline.core.theme.SkylineColors
import com.dragonic.skyline.presentation.components.*

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val infiniteTransition = rememberInfiniteTransition(label = "login")
    var entered by remember { mutableStateOf(false) }
    val alpha   by animateFloatAsState(if (entered) 1f else 0f, tween(600), label = "login_alpha")
    val yOffset by animateFloatAsState(if (entered) 0f else 80f, spring(0.8f, 300f), label = "login_y")

    LaunchedEffect(Unit) { entered = true }
    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess) onLoginSuccess()
    }

    // Float animation for card
    val float by infiniteTransition.animateFloat(
        -6f, 6f,
        infiniteRepeatable(tween(3000, easing = EaseInOutSine), RepeatMode.Reverse),
        label = "float"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Video / nebula background
        if (uiState.bgVideoUri != null) {
            VideoBackground(
                videoUri = uiState.bgVideoUri,
                modifier = Modifier.fillMaxSize()
            )
        }
        NebulaBackground(Modifier.fillMaxSize())
        SakuraParticleOverlay(Modifier.fillMaxSize(), count = 25)
        CyberGridBackground(Modifier.fillMaxSize())
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SkylineColors.DeepVoid.copy(alpha = 0.55f))
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Box(modifier = Modifier.offset(y = float.dp)) {
                ReactorLogo(size = 160.dp)
            }

            Spacer(Modifier.height(20.dp))

            Text(
                "SKYLINE",
                style = MaterialTheme.typography.displaySmall,
                color = SkylineColors.SakuraPink,
                modifier = Modifier.alpha(alpha)
            )
            Text(
                "CONTROL CENTER",
                style = MaterialTheme.typography.headlineSmall,
                color = SkylineColors.CyberCyan,
                modifier = Modifier.alpha(alpha)
            )

            Spacer(Modifier.height(32.dp))

            // Glass login card
            GlassCard(
                modifier     = Modifier
                    .fillMaxWidth()
                    .offset(y = yOffset.dp)
                    .alpha(alpha),
                animated     = true,
                glowColor    = SkylineColors.SakuraPinkGlow,
                cornerRadius = 20.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "SIGN IN",
                        style = MaterialTheme.typography.headlineMedium,
                        color = SkylineColors.TextPrimary
                    )
                    Text(
                        "Akses sistem kontrol Skyline",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SkylineColors.TextSecondary
                    )

                    // Username field
                    SkylineTextField(
                        value       = uiState.username,
                        onValueChange = viewModel::onUsernameChange,
                        label       = "Username",
                        leadingIcon = Icons.Rounded.Person,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                    )

                    // Password field
                    SkylineTextField(
                        value       = uiState.password,
                        onValueChange = viewModel::onPasswordChange,
                        label       = "Password",
                        leadingIcon = Icons.Rounded.Lock,
                        isPassword  = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                    )

                    // Remember Me
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked  = uiState.rememberMe,
                            onCheckedChange = viewModel::onRememberMeChange,
                            colors   = CheckboxDefaults.colors(
                                checkedColor = SkylineColors.SakuraPink
                            )
                        )
                        Text(
                            "Ingat saya",
                            style = MaterialTheme.typography.bodyMedium,
                            color = SkylineColors.TextSecondary
                        )
                    }

                    // Error message
                    if (uiState.error != null) {
                        Text(
                            uiState.error!!,
                            style = MaterialTheme.typography.bodySmall,
                            color = SkylineColors.StatusOffline
                        )
                    }

                    // Login Button
                    Button(
                        onClick  = viewModel::onLogin,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .neonGlowBorder(SkylineColors.SakuraPink),
                        colors   = ButtonDefaults.buttonColors(
                            containerColor = SkylineColors.SakuraPink.copy(alpha = 0.2f)
                        ),
                        shape    = RoundedCornerShape(12.dp)
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                color    = SkylineColors.SakuraPink,
                                modifier = Modifier.size(22.dp)
                            )
                        } else {
                            Text(
                                "MASUK",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.White
                            )
                        }
                    }

                    // Biometric button
                    OutlinedButton(
                        onClick  = viewModel::onBiometricLogin,
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        border   = BorderStroke(1.dp, SkylineColors.CyberCyan.copy(alpha = 0.5f)),
                        shape    = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            Icons.Rounded.Fingerprint,
                            contentDescription = null,
                            tint    = SkylineColors.CyberCyan,
                            modifier= Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Fingerprint / Biometrik",
                            style = MaterialTheme.typography.bodyMedium,
                            color = SkylineColors.CyberCyan
                        )
                    }

                    // Register link
                    TextButton(
                        onClick  = { /* navigate to register */ },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            "Belum punya akun? Daftar",
                            style = MaterialTheme.typography.bodySmall,
                            color = SkylineColors.SakuraPinkLight
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SkylineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var showPassword by remember { mutableStateOf(false) }
    OutlinedTextField(
        value         = value,
        onValueChange = onValueChange,
        label         = { Text(label, color = SkylineColors.TextMuted) },
        leadingIcon   = {
            Icon(leadingIcon, null, tint = SkylineColors.SakuraPink)
        },
        trailingIcon  = if (isPassword) ({
            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    if (showPassword) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                    contentDescription = null,
                    tint = SkylineColors.TextMuted
                )
            }
        }) else null,
        visualTransformation = if (isPassword && !showPassword)
            PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions  = keyboardOptions,
        singleLine       = true,
        modifier         = Modifier.fillMaxWidth(),
        colors           = OutlinedTextFieldDefaults.colors(
            focusedBorderColor   = SkylineColors.SakuraPink,
            unfocusedBorderColor = SkylineColors.GlassBorder,
            focusedTextColor     = SkylineColors.TextPrimary,
            unfocusedTextColor   = SkylineColors.TextPrimary,
            cursorColor          = SkylineColors.SakuraPink
        ),
        shape = RoundedCornerShape(12.dp)
    )
}
