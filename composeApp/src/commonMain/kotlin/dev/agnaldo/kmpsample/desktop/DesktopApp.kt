package dev.agnaldo.kmpsample.desktop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.agnaldo.kmpsample.designsystem.theme.ApplicationTheme
import dev.agnaldo.kmpsample.designsystem.widgets.ButtonContrast
import dev.agnaldo.kmpsample.designsystem.widgets.ButtonError
import dev.agnaldo.kmpsample.designsystem.widgets.ButtonGhost
import dev.agnaldo.kmpsample.designsystem.widgets.ButtonPrimary
import dev.agnaldo.kmpsample.designsystem.widgets.ButtonSecondary
import dev.agnaldo.kmpsample.shared.providers.VariantStrings
import dev.agnaldo.kmpsample.shared.providers.FlavorStrings
import dev.agnaldo.kmpsample.shared.providers.Strings
import dev.agnaldo.kmpsample.shared.providers.getLanguage
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Suppress("ktlint:standard:function-naming")
@Composable
@Preview
fun DesktopApp() {
    var buttonEnabled by remember { mutableStateOf(true) }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    ApplicationTheme {
        Scaffold(
            scaffoldState = scaffoldState,
        ) {
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                Column(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ButtonPrimary(
                        text = FlavorStrings.click1.localize(),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = buttonEnabled,
                        onClick = { buttonEnabled = !buttonEnabled }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ButtonSecondary(
                        text = getLanguage().supportedLanguage.value,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            coroutineScope.launch {
                                buttonEnabled = !buttonEnabled
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Button 1 clicked! " + getLanguage().name,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    )
                    ButtonContrast(
                        text = Strings.hello.localize("Agnaldo"),
                        modifier = Modifier.fillMaxWidth(),
                    )
                    ButtonGhost(
                        text = VariantStrings.variantStr.localize(),
                        modifier = Modifier.fillMaxWidth(),
                    )
                    ButtonGhost(
                        text = "Click me 5!",
                        enabled = false,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    ButtonError(
                        text = "Click me 6!",
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}
