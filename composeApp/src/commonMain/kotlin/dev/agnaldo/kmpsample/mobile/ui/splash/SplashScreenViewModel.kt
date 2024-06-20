package dev.agnaldo.kmpsample.mobile.ui.splash

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SplashScreenViewModel : ViewModel() {

    private val _goToLogin = MutableStateFlow(false)
    val goToLogin: StateFlow<Boolean> = _goToLogin.asStateFlow()

    fun init() { }

    fun onAnimationProgressChanged(progress: Float) {
        if (progress == 1F) {
            _goToLogin.value = true
        }
    }
}
