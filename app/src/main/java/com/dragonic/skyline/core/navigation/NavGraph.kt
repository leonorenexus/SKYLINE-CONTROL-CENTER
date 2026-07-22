package com.dragonic.skyline.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dragonic.skyline.presentation.analytics.AnalyticsScreen
import com.dragonic.skyline.presentation.devices.DevicesScreen
import com.dragonic.skyline.presentation.home.HomeScreen
import com.dragonic.skyline.presentation.iot.IoTScreen
import com.dragonic.skyline.presentation.login.LoginScreen
import com.dragonic.skyline.presentation.profile.ProfileScreen
import com.dragonic.skyline.presentation.settings.SettingsScreen
import com.dragonic.skyline.presentation.splash.SplashScreen
import com.dragonic.skyline.presentation.wifi.WifiScreen

sealed class Screen(val route: String) {
    object Splash    : Screen("splash")
    object Login     : Screen("login")
    object Home      : Screen("home")
    object Devices   : Screen("devices")
    object Wifi      : Screen("wifi")
    object IoT       : Screen("iot")
    object Analytics : Screen("analytics")
    object Profile   : Screen("profile")
    object Settings  : Screen("settings")
}

@Composable
fun SkylineNavGraph(navController: NavHostController) {
    NavHost(
        navController    = navController,
        startDestination = Screen.Splash.route,
        enterTransition  = {
            fadeIn(tween(400)) + slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start, tween(400)
            )
        },
        exitTransition  = {
            fadeOut(tween(300)) + slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start, tween(300)
            )
        },
        popEnterTransition = {
            fadeIn(tween(400)) + slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End, tween(400)
            )
        },
        popExitTransition  = {
            fadeOut(tween(300)) + slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End, tween(300)
            )
        }
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onFinish = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Home.route)      { HomeScreen(navController) }
        composable(Screen.Devices.route)   { DevicesScreen(navController) }
        composable(Screen.Wifi.route)      { WifiScreen(navController) }
        composable(Screen.IoT.route)       { IoTScreen(navController) }
        composable(Screen.Analytics.route) { AnalyticsScreen(navController) }
        composable(Screen.Profile.route)   { ProfileScreen(navController) }
        composable(Screen.Settings.route)  { SettingsScreen(navController) }
    }
}
