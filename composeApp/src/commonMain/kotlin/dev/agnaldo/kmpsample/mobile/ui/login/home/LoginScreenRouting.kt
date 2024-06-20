package dev.agnaldo.kmpsample.mobile.ui.login.home

sealed class LoginScreenRouting {
    data object Home : LoginScreenRouting()
    data object ForgotPass : LoginScreenRouting()
    data object Register : LoginScreenRouting()
}
