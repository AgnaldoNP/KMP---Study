@file:Suppress("ktlint:standard:function-naming")

package dev.agnaldo.kmpsample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.agnaldo.kmpsample.ui.desktop.DesktopApp
import dev.agnaldo.kmpsample.ui.mobile.MobileApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            MobileApp()
            DesktopApp()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
//    MobileApp()
    DesktopApp()
}
