package dev.agnaldo.kmpsample.shared.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.awt.Desktop
import java.net.URI

@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    return remember { PermissionsManager(callback) }
}

actual class PermissionsManager actual constructor(
    private val callback: PermissionCallback
) : PermissionHandler {
    @Composable
    override fun askPermission(permission: PermissionType) {
        when (permission) {
            PermissionType.CAMERA -> {
                if (isMacOS()) {
                    requestMacOSCameraPermission()
                } else {
                    callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
                }
            }
            PermissionType.GALLERY -> {
                if (isMacOS()) {
                    requestMacOSGalleryPermission()
                } else {
                    callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
                }
            }
        }
    }

    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        return when (permission) {
            PermissionType.CAMERA -> {
                if (isMacOS()) {
                    checkMacOSCameraPermission()
                } else {
                    true
                }
            }
            PermissionType.GALLERY -> {
                if (isMacOS()) {
                    checkMacOSGalleryPermission()
                } else {
                    true
                }
            }
        }
    }

    @Composable
    override fun launchSettings() {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(URI("x-apple.systempreferences:com.apple.preference.security?Privacy_Camera"))
        }
    }

    private fun isMacOS(): Boolean {
        return System.getProperty("os.name").lowercase().contains("mac")
    }

    private fun requestMacOSCameraPermission() {
        // Implement macOS specific camera permission request logic
    }

    private fun requestMacOSGalleryPermission() {
        // Implement macOS specific gallery permission request logic
    }

    private fun checkMacOSCameraPermission(): Boolean {
//        val authorizationStatus = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
//        return when (authorizationStatus) {
//            AVAuthorizationStatusAuthorized -> true
//            AVAuthorizationStatusDenied, AVAuthorizationStatusRestricted -> false
//            AVAuthorizationStatusNotDetermined -> false // Permission not requested yet
//            else -> false
//        }
        return true
    }

    private fun checkMacOSGalleryPermission(): Boolean {
        // Implement macOS specific gallery permission check logic
        return true
    }
}
