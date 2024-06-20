@file:Suppress("ktlint:standard:function-naming")

package dev.agnaldo.kmpsample.mobile.ui.login.register

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import dev.agnaldo.kmpsample.designsystem.theme.AppTheme
import dev.agnaldo.kmpsample.designsystem.theme.ApplicationTheme
import dev.agnaldo.kmpsample.shared.di.ComponentInjector.inject
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.arrow_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Suppress("ktlint:standard:function-naming")
@Composable
@Preview
fun RegisterScreen() {
    val viewModel = inject<RegisterScreenViewModel>().also { it.init() }
    val navController = inject<NavHostController>()

    ApplicationTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Register", color = AppTheme.colors.onPrimary) },
                    backgroundColor = AppTheme.colors.primary,
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painter = painterResource(Res.drawable.arrow_back),
                                tint = AppTheme.colors.onPrimary,
                                contentDescription = "Voltar"
                            )
                        }
                    }
                )
            }
        ) {
            // Content of the HomeScreen
        }
    }
}
