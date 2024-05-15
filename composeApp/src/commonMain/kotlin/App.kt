import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.agnaldo.kmpsample.designsystem.theme.ApplicationTheme
import dev.agnaldo.kmpsample.designsystem.theme.AppTheme
import dev.agnaldo.kmpsample.designsystem.colors.AppColors
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    var isDarkTheme by remember { mutableStateOf(false) }
    ApplicationTheme(
        colors = AppColors.getColorsTheme(isDarkTheme)
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .background(AppTheme.colors.primary)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier.padding(0.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Yellow
                    ),
                    onClick = {
                        isDarkTheme = !isDarkTheme
                    }

                ) {
                    Text("Click me!")
                    // onclick
                    KeyboardActions {
                        // show alert
                    }
                }
            }
        }
    }
}

//        if (updateColors) {
//            MaterialTheme {
//                AppTheme.colors.updateColorsFrom(DarkColors)
//            }
//        }
//        Box(
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxWidth()
//                .fillMaxHeight()
//        ) {
//            Column(
//                modifier = Modifier
//                    .background(MaterialTheme.colors.error)
//                    .background(AppTheme.colors.primaryAbc)
//                    .fillMaxWidth()
//                    .fillMaxHeight(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Button(
//                    modifier = Modifier.padding(0.dp),
//                    shape = RoundedCornerShape(8.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        backgroundColor = Color.Yellow
//                    ),
//                    onClick = {
//                        showSnackbar = true
//                        showContent = !showContent
//                        updateColors = !updateColors
//                    }
//
//                ) {
//                    Text("Click me!")
//                    // onclick
//                    KeyboardActions {
//                        // show alert
//                    }
//                }
//
//                AnimatedVisibility(showContent) {
//                    val greeting = remember { Greeting().greet() }
//                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                        Image(painterResource(Res.drawable.compose_multiplatform), null)
//                        Text("Compose: $greeting")
//                    }
//                }
//            }
//
//            if (showSnackbar) {
//                Snackbar(
//                    action = {
//                        Button(onClick = { showSnackbar = false }) {
//                            Text("Dismiss")
//                        }
//                    },
//                    modifier = Modifier.padding(8.dp).align(Alignment.BottomCenter)
//                ) {
//                    Text(text = "This is a message")
//                }
//            }
//        }
