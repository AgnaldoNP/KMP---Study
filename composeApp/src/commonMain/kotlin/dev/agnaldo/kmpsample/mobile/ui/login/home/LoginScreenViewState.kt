package dev.agnaldo.kmpsample.mobile.ui.login.home

data class LoginScreenViewState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val showError: Boolean = false
)
