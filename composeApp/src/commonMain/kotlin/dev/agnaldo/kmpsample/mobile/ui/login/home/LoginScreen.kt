@file:Suppress("ktlint:standard:function-naming")

package dev.agnaldo.kmpsample.mobile.ui.login.home

import FullScreenLoading
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import dev.agnaldo.kmpsample.designsystem.theme.AppTheme
import dev.agnaldo.kmpsample.designsystem.theme.ApplicationTheme
import dev.agnaldo.kmpsample.designsystem.widgets.AppDialogBottomSheet
import dev.agnaldo.kmpsample.designsystem.widgets.AppInputText
import dev.agnaldo.kmpsample.designsystem.widgets.AppText
import dev.agnaldo.kmpsample.designsystem.widgets.ButtonPrimary
import dev.agnaldo.kmpsample.designsystem.widgets.ImageButton
import dev.agnaldo.kmpsample.mobile.ui.Screen
import dev.agnaldo.kmpsample.shared.di.ComponentInjector.inject
import dev.agnaldo.kmpsample.shared.providers.Strings
import dev.agnaldo.kmpsample.shared.utils.parseHtml
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.ic_facebook
import kotlinproject.composeapp.generated.resources.ic_google
import kotlinproject.composeapp.generated.resources.ic_phone
import kotlinproject.composeapp.generated.resources.logo_login
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun LoginScreen() {
    val viewModel = remember { inject<LoginScreenViewModel>().also { it.init() } }
    if (handleRouting(viewModel)) return
    val viewState = viewModel.viewState.collectAsState().value ?: return

    ApplicationTheme {
        Box {
            Scaffold {
                Column(
                    modifier = Modifier
                        .background(AppTheme.colors.background)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clipToBounds()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LogoImage()

                    Spacer(modifier = Modifier.padding(8.dp))
                    LoginImageButtons(viewModel)

                    Spacer(modifier = Modifier.padding(8.dp))
                    LoginEmailAndPass(viewModel, viewState)

                    Spacer(modifier = Modifier.padding(16.dp))
                    ButtonPrimary(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 100.dp),
                        text = Strings.login.localize(),
                        onClick = viewModel::onLoginClick
                    )

                    Spacer(modifier = Modifier.padding(4.dp))
                    ForgotPass(viewModel)

                    Spacer(modifier = Modifier.weight(1F))
                    CreateAccount(viewModel)
                    Spacer(modifier = Modifier.padding(bottom = 16.dp))
                }
            }

            if (viewState.showError) {
                AppDialogBottomSheet(
                    title = Strings.loginErrorTitle.localize(),
                    message = Strings.loginErrorMessage.localize(),
                )
            }

            if (viewState.isLoading) {
                FullScreenLoading()
            }
        }
    }
}

@Composable
private fun handleRouting(viewModel: LoginScreenViewModel): Boolean {
    val routing = viewModel.routing.collectAsStateWithLifecycle(initialValue = null).value ?: return false
    val navController = remember { inject<NavHostController>() }
    when (routing) {
        is LoginScreenRouting.Home -> navController.navigate(Screen.Home.name)
        is LoginScreenRouting.ForgotPass -> navController.navigate(Screen.ForgotPass.name)
        is LoginScreenRouting.Register -> navController.navigate(Screen.Register.name)
    }
    viewModel.resetRouting()
    return true
}

@Composable
private fun LogoImage() {
    Image(
        painter = painterResource(Res.drawable.logo_login),
        contentDescription = Strings.loginLogoContentDescription.localize(),
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth,
    )
}

@Composable
private fun LoginImageButtons(viewModel: LoginScreenViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppText(
            Strings.loginWith.localize().parseHtml(),
            style = AppTheme.typography.caption,
            color = AppTheme.colors.baseMiddle
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ImageButton(
                painterResource(Res.drawable.ic_phone),
                onClick = viewModel::onLoginByPhoneClick
            )
            ImageButton(
                painterResource(Res.drawable.ic_google),
                onClick = viewModel::onLoginByGoogleClick
            )
            ImageButton(
                painterResource(Res.drawable.ic_facebook),
                onClick = viewModel::onLoginByFacebookClick
            )
        }
    }
}

@Composable
private fun LoginEmailAndPass(
    viewModel: LoginScreenViewModel,
    viewState: LoginScreenViewState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppText(
            Strings.or.localize(),
            style = AppTheme.typography.caption,
            color = AppTheme.colors.baseMiddle
        )

        Spacer(modifier = Modifier.padding(8.dp))
        AppInputText(
            label = Strings.loginEmailLabel.localize(),
            placeHolder = Strings.loginEmailPlaceHolder.localize(),
            value = viewState.email,
            onValueChange = viewModel::onEmailChanged
        )
        Spacer(modifier = Modifier.padding(8.dp))
        AppInputText(
            label = Strings.loginPassLabel.localize(),
            placeHolder = Strings.loginPassPlaceHolder.localize(),
            password = true,
            value = viewState.password,
            onValueChange = viewModel::onPasswordChanged
        )
    }
}

@Composable
private fun ForgotPass(viewModel: LoginScreenViewModel) {
    AppText(
        text = Strings.forgotPass.localize(),
        color = AppTheme.colors.baseMiddle,
        style = AppTheme.typography.caption,
        onClick = { viewModel.onForgotPassClick() }
    )
}

@Composable
private fun CreateAccount(viewModel: LoginScreenViewModel) {
    AppText(
        text = Strings.noLoginCreateAccount.localize().parseHtml(),
        color = AppTheme.colors.baseMiddle,
        style = AppTheme.typography.caption,
        onLinkClick = { viewModel.onCreateAccountClick() }
    )
}
