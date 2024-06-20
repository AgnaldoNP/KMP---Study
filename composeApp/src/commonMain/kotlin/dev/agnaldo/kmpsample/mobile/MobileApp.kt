package dev.agnaldo.kmpsample.mobile

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.agnaldo.kmpsample.mobile.ui.Screen
import dev.agnaldo.kmpsample.mobile.ui.home.HomeScreen
import dev.agnaldo.kmpsample.mobile.ui.login.forgotpassword.ForgotPasswordScreen
import dev.agnaldo.kmpsample.mobile.ui.login.home.LoginScreen
import dev.agnaldo.kmpsample.mobile.ui.login.register.RegisterScreen
import dev.agnaldo.kmpsample.mobile.ui.splash.SplashScreen
import dev.agnaldo.kmpsample.shared.di.ComponentInjector
import org.jetbrains.compose.ui.tooling.preview.Preview

@Suppress("ktlint:standard:function-naming")
@Composable
@Preview
fun MobileApp() {
    val navController = rememberNavController()
    ComponentInjector.loadInjectableComponent(navController)

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.name
    ) {
        composable(Screen.Splash.name) { SplashScreen() }
        composable(Screen.Login.name) { LoginScreen() }
        composable(Screen.ForgotPass.name) { ForgotPasswordScreen() }
        composable(Screen.Register.name) { RegisterScreen() }
        composable(Screen.Home.name) { HomeScreen() }
    }
}
