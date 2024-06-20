package dev.agnaldo.kmpsample.mobile

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.agnaldo.kmpsample.mobile.ui.Screen
import dev.agnaldo.kmpsample.mobile.ui.login.home.LoginScreen
import dev.agnaldo.kmpsample.mobile.ui.splash.SplashScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Suppress("ktlint:standard:function-naming")
@Composable
@Preview
fun MobileApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.Login.name) {
        composable(Screen.Splash.name) { SplashScreen(navController) }
        composable(Screen.Login.name) { LoginScreen(navController) }
    }
}
