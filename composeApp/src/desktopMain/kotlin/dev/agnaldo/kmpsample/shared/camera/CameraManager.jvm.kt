
@file:Suppress("ktlint:standard:function-naming")
package dev.agnaldo.kmpsample.shared.camera

import FullScreenLoading
import MyApplicationState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import com.github.eduramiba.webcamcapture.drivers.NativeDriver
import com.github.sarxos.webcam.Webcam
import dev.agnaldo.kmpsample.mobile.utils.RawResource
import dev.agnaldo.kmpsample.shared.di.ComponentInjector
import dev.agnaldo.kmpsample.shared.image.SharedImage
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.ic_shutter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.skia.Image
import org.jetbrains.skiko.toImage
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import javax.sound.sampled.AudioSystem

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class CameraManager actual constructor(
    private val onLaunch: @Composable () -> Unit
) {
    @Composable
    actual fun launch() {
        onLaunch()
    }
}

private val cameraWindowsWidth = 900.dp
private var cameraWindowsHeight = 0.dp
private val cameraWindowId = "camera"

@Composable
actual fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager {
    val applicationState = ComponentInjector.inject<MyApplicationState>()
    var webcam: Webcam? = null

    return CameraManager {
        val coroutineScope = rememberCoroutineScope()
        var addCameraWindow by remember { mutableStateOf(false) }
        FullScreenLoading()

        coroutineScope.launch(Dispatchers.IO) {
            Webcam.setDriver(NativeDriver())
            webcam = Webcam.getDefault().also {
                val webCamWidth = it.device.resolution.width
                val webCamHeight = it.device.resolution.height
                val aspectRatio = webCamHeight.toDouble() / webCamWidth.toDouble()
                cameraWindowsHeight = (cameraWindowsWidth.value * aspectRatio).toInt().dp
                addCameraWindow = true
            }
        }

        if (addCameraWindow) {
            applicationState.openNewWindow(cameraWindowId) {
                val windowState = rememberWindowState(
                    size = DpSize(
                        width = cameraWindowsWidth,
                        height = cameraWindowsHeight
                    )
                )
                Window(
                    onCloseRequest = {
                        applicationState.closeWindow(cameraWindowId)
                        if (webcam?.isOpen == true) {
                            webcam?.close()
                        }
                        onResult(null)
                    },
                    alwaysOnTop = true,
                    state = windowState,
                    title = "Camera"
                ) {
                    webcam?.let {
                        CameraSurface(
                            webcam = it,
                            windowState = windowState,
                            applicationState = applicationState,
                            onResult = onResult
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CameraSurface(
    webcam: Webcam,
    windowState: WindowState,
    applicationState: MyApplicationState,
    onResult: (SharedImage?) -> Unit
) {
    var bufferedImage by remember { mutableStateOf<BufferedImage?>(null) }
    var previewImage by remember { mutableStateOf<Image?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            webcam.open()

            while (webcam.isOpen) {
                val webCamBufferedImage = webcam.image
                bufferedImage = BufferedImage(
                    webCamBufferedImage.width,
                    webCamBufferedImage.height,
                    BufferedImage.TYPE_INT_RGB
                )

                val graphics = bufferedImage!!.graphics
                graphics.drawImage(webCamBufferedImage, 0, 0, null)
                graphics.dispose()
                windowState.size.width

                previewImage = bufferedImage!!.resizeBufferedImage(
                    windowState.size.width.value,
                    windowState.size.height.value
                ).toImage()
                delay(1000 / 50)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            previewImage?.let {
                drawIntoCanvas { canvas ->
                    canvas.nativeCanvas.drawImage(it, 0f, 0f)
                }
            }
        }
        Image(
            painter = painterResource(Res.drawable.ic_shutter),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
                .pointerInput(Unit) {
                    detectTapGestures {
                        coroutineScope.launch(Dispatchers.IO) {
                            takePicture(
                                bufferedImage = bufferedImage,
                                webcam = webcam,
                                applicationState = applicationState,
                                onResult = onResult
                            )
                        }
                    }
                }
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
suspend fun takePicture(
    bufferedImage: BufferedImage?,
    webcam: Webcam,
    applicationState: MyApplicationState,
    onResult: (SharedImage?) -> Unit
) {
    if (bufferedImage == null) return

    AudioSystem.getClip().apply {
        val bytes = Res.readBytes(RawResource.SHUTTER.path)
        val bais = ByteArrayInputStream(bytes)
        open(AudioSystem.getAudioInputStream(bais))
        framePosition = 0
        start()
    }

    webcam.close()
    onResult.invoke(SharedImage(bufferedImage))
    applicationState.closeWindow(cameraWindowId)
}

fun BufferedImage.resizeBufferedImage(
    targetWidth: Float,
    targetHeight: Float
): BufferedImage {
    val resizedImage = BufferedImage(
        targetWidth.toInt(),
        targetHeight.toInt(),
        this.type
    )
    val graphics2D = resizedImage.createGraphics()
    graphics2D.drawImage(
        this,
        0,
        0,
        targetWidth.toInt(),
        targetHeight.toInt(),
        null
    )
    graphics2D.dispose()
    return resizedImage
}
