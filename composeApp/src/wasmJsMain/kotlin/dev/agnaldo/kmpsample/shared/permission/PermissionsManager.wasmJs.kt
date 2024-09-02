package dev.agnaldo.kmpsample.shared.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    return remember { PermissionsManager(callback) }
}

actual class PermissionsManager actual constructor(
    private val callback: PermissionCallback
) : PermissionHandler {
    @Composable
    override fun askPermission(permission: PermissionType) {
    }

    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        return true
    }

    @Composable
    override fun launchSettings() {
    }
}
