@file:Suppress("ktlint:standard:function-naming")

package dev.agnaldo.kmpsample.mobile.ui.login.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.agnaldo.kmpsample.designsystem.theme.AppTheme
import dev.agnaldo.kmpsample.designsystem.theme.ApplicationTheme
import dev.agnaldo.kmpsample.designsystem.widgets.AppDialogBottomSheet
import dev.agnaldo.kmpsample.shared.di.ComponentInjector.inject
import dev.agnaldo.kmpsample.shared.camera.rememberCameraManager
import dev.agnaldo.kmpsample.shared.camera.rememberGalleryManager
import dev.agnaldo.kmpsample.shared.permission.PermissionCallback
import dev.agnaldo.kmpsample.shared.permission.PermissionStatus
import dev.agnaldo.kmpsample.shared.permission.PermissionType
import dev.agnaldo.kmpsample.shared.permission.createPermissionsManager
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.arrow_back
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Suppress("ktlint:standard:function-naming")
@Composable
@Preview
fun RegisterScreen() {
    val viewModel = inject<RegisterScreenViewModel>().also { it.init() }
    val navController = inject<NavHostController>()

    val coroutineScope = rememberCoroutineScope()
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var launchCamera by remember { mutableStateOf(value = false) }
    var launchGallery by remember { mutableStateOf(value = false) }
    var launchSetting by remember { mutableStateOf(value = false) }
    var permissionRationalDialog by remember { mutableStateOf(value = false) }

    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(
            permissionType: PermissionType,
            status: PermissionStatus
        ) {
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionType.CAMERA -> launchCamera = true
                        PermissionType.GALLERY -> launchGallery = true
                    }
                }

                else -> {
                    permissionRationalDialog = true
                }
            }
        }
    })

    val cameraManager = rememberCameraManager {
        launchCamera = false
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) { it?.toImageBitmap() }
            imageBitmap = bitmap
        }
    }

    val galleryManager = rememberGalleryManager {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) { it?.toImageBitmap() }
            imageBitmap = bitmap
        }
    }

    ApplicationTheme {
        Box {
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
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    Button(onClick = {
                        launchCamera = true
//                    viewModel.onStartPhotoTaking()
                    }) {
                        Text("Take Photo")
                    }
                    Button(onClick = {
                        launchGallery = true
//                    viewModel.onPickPhoto()
                    }) {
                        Text("Pick Photo")
                    }
                    if (imageBitmap != null) {
                        Image(
                            bitmap = imageBitmap!!,
                            contentDescription = "Profile",
                            modifier = Modifier.size(100.dp).clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            if (launchCamera) {
                if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
                    cameraManager.launch()
                } else {
                    permissionsManager.askPermission(PermissionType.CAMERA)
                }
            }

            if (launchGallery) {
                if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
                    galleryManager.launch()
                } else {
                    permissionsManager.askPermission(PermissionType.GALLERY)
                }
                launchGallery = false
            }

            if (launchSetting) {
                permissionsManager.launchSettings()
                launchSetting = false
            }

            if (permissionRationalDialog) {
                AppDialogBottomSheet(
                    title = "Permission Required",
                    message = "To set your profile picture, please grant this permission. You can manage permissions in your device settings.",
                    okButtonText = "Settings",
                    onOkClick = {
                        permissionRationalDialog = false
                        launchSetting = true
                    },
                    cancelButtonText = "Cancel",
                    onCancelClick = {
                        permissionRationalDialog = false
                    }
                )
            }
        }
    }
}
