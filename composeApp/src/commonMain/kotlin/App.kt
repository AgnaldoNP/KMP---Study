import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    var buttonEnabled by remember { mutableStateOf(true) }
    ApplicationTheme {
        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ButtonPrimary(
                    text = "Click me 1!",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = buttonEnabled,
                    onClick = { buttonEnabled = !buttonEnabled }
                )
                Spacer(modifier = Modifier.weight(1f))
                ButtonSecondary(
                    text = "Click me 2!",
                    modifier = Modifier.fillMaxWidth(),
                )
                ButtonContrast(
                    text = "Click me 3!",
                    modifier = Modifier.fillMaxWidth(),
                )
                ButtonGhost(
                    text = "Click me 4!",
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
