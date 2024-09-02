package dev.agnaldo.kmpsample.mobile.ui.login.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.agnaldo.kmpsample.shared.repository.datasource.remote.util.RequestError
import dev.agnaldo.kmpsample.shared.repository.datasource.remote.util.subscribe
import dev.agnaldo.kmpsample.shared.repository.feature.login.LoginRepository
import dev.agnaldo.kmpsample.shared.repository.model.login.LoginSuccessResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow<LoginScreenViewState?>(LoginScreenViewState())
    val viewState: StateFlow<LoginScreenViewState?> = _viewState.asStateFlow()

    private val _routing = MutableStateFlow<LoginScreenRouting?>(null)
    val routing: StateFlow<LoginScreenRouting?> = _routing.asStateFlow()

    fun init() {
        "".toString()
    }

    fun onLoginByPhoneClick() {
    }

    fun onLoginByGoogleClick() {
    }

    fun onLoginByFacebookClick() {
    }

    fun onEmailChanged(email: String) {
        _viewState.value = _viewState.value?.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        _viewState.value = _viewState.value?.copy(password = password)
    }

    fun onLoginClick() {
        viewModelScope.launch {
            val user = _viewState.value?.email.orEmpty()
            val pass = _viewState.value?.password.orEmpty()
            _viewState.value = _viewState.value?.copy(
                isLoading = true,
                showError = false
            )
            loginRepository.login(user = user, pass = pass).subscribe(
                ::onLoginSuccess,
                ::onLoginError
            )
        }
    }

    private fun onLoginSuccess(loginResponse: LoginSuccessResponse) {
        _viewState.update { _viewState.value?.copy(isLoading = false) }
        _routing.update { LoginScreenRouting.ForgotPass }
        loginResponse.toString()
    }

    private fun onLoginError(error: RequestError) {
        _viewState.value = _viewState.value?.copy(
            isLoading = false,
            showError = true
        )
        error.toString()
    }

    fun onForgotPassClick() {
        _routing.update { LoginScreenRouting.ForgotPass }
    }

    fun onCreateAccountClick() {
        _routing.update { LoginScreenRouting.Register }
    }

    fun resetRouting() {
        _routing.value = null
    }
}
