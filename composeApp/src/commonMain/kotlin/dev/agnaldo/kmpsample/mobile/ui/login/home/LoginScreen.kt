@file:Suppress("ktlint:standard:function-naming")

package dev.agnaldo.kmpsample.mobile.ui.login.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.agnaldo.kmpsample.designsystem.theme.AppTheme
import dev.agnaldo.kmpsample.designsystem.theme.ApplicationTheme
import dev.agnaldo.kmpsample.designsystem.widgets.AppInputText
import dev.agnaldo.kmpsample.designsystem.widgets.ButtonPrimary
import dev.agnaldo.kmpsample.designsystem.widgets.ImageButton
import dev.agnaldo.kmpsample.providers.Strings
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.ic_facebook
import kotlinproject.composeapp.generated.resources.ic_google
import kotlinproject.composeapp.generated.resources.ic_phone
import kotlinproject.composeapp.generated.resources.logo_login
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController)
}

@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel = viewModel { LoginScreenViewModel() }.also { it.init() }

    ApplicationTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .background(AppTheme.colors.background)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LogoImage()

                Spacer(modifier = Modifier.padding(8.dp))
                LoginImageButtons(viewModel)

                Spacer(modifier = Modifier.padding(8.dp))
                LoginEmailAndPass(viewModel)

                Spacer(modifier = Modifier.padding(16.dp))
                ButtonPrimary(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 100.dp),
                    text = Strings.login.localize(),
                    onClick = viewModel::onLoginClick
                )
            }
        }
    }
}

@Composable
private fun LogoImage() {
    Image(
        painter = painterResource(Res.drawable.logo_login),
        contentDescription = "My Image",
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

        Text(
            Strings.loginWith.localize(),
            style = AppTheme.typography.caption,
            color = AppTheme.colors.baseMiddle
        )
    }
}

@Composable
private fun LoginEmailAndPass(viewModel: LoginScreenViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            Strings.or.localize(),
            style = AppTheme.typography.caption,
            color = AppTheme.colors.baseMiddle
        )

        Spacer(modifier = Modifier.padding(8.dp))
        AppInputText(
            label = "Email",
            placeHolder = "email@host.com",
            onValueChange = viewModel::onEmailChanged
        )
        Spacer(modifier = Modifier.padding(8.dp))
        AppInputText(
            label = "Password",
            placeHolder = "********",
            password = true,
            onValueChange = viewModel::onPasswordChanged
        )
        // todo add "forgot password" right aligned
    }
}
