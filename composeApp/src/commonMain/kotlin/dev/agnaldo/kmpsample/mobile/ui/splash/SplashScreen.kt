@file:OptIn(ExperimentalResourceApi::class)

package dev.agnaldo.kmpsample.mobile.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import dev.agnaldo.kmpsample.designsystem.theme.ApplicationTheme
import dev.agnaldo.kmpsample.mobile.ui.Screen
import dev.agnaldo.kmpsample.mobile.utils.RawResource
import dev.agnaldo.kmpsample.mobile.utils.RawResource.Companion.getTextContent
import dev.agnaldo.kmpsample.shared.di.ComponentInjector.inject
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview

@Suppress("ktlint:standard:function-naming")
@Composable
@Preview
fun SplashScreen() {
    val viewModel = inject<SplashScreenViewModel>().also { it.init() }
    val navController = inject<NavHostController>()

    val goToLogin by viewModel.goToLogin.collectAsState()
    if (goToLogin) {
        navController.navigate(Screen.Login.name)
        return
    }

    ApplicationTheme {
        Scaffold {
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                var splashLottie by remember { mutableStateOf("") }
                LaunchedEffect(key1 = Unit) { splashLottie = RawResource.SPLASH.getTextContent() }

                val composition by rememberLottieComposition(LottieCompositionSpec.JsonString(splashLottie))
                val animationState = animateLottieCompositionAsState(composition = composition, iterations = 1)
                LaunchedEffect(key1 = animationState.progress) { viewModel.onAnimationProgressChanged(animationState.progress) }

                LottieAnimation(
                    composition = composition,
                    progress = { animationState.progress },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
