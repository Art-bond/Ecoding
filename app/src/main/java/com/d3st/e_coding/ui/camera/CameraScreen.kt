package com.d3st.e_coding.ui.camera

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.concurrent.futures.await
import androidx.lifecycle.LifecycleOwner


/**
 * Camera Screen
 */
@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    onTakePhoto: (ImageCapture) -> Unit,
) {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = modifier.fillMaxSize()
    ) {
        CameraView(onTakePhoto = onTakePhoto)
    }
}

@Composable
fun CameraView(
    onTakePhoto: (ImageCapture) -> Unit,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    context: Context = LocalContext.current,
) {
    // 1
    val lensFacing = CameraSelector.LENS_FACING_BACK

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    val cameraProviderFeature = ProcessCameraProvider.getInstance(context)

    // 2
    LaunchedEffect(lensFacing) {
        var cameraProvider = cameraProviderFeature.await()

        // CameraProvider
        cameraProvider = cameraProvider
            ?: throw IllegalStateException("Camera initialization failed.")

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    // 3
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            modifier = Modifier.padding(bottom = 20.dp),
            onClick = {
                Log.i("oldRepublic", "ON CLICK")
                onTakePhoto(imageCapture)
            },
            content = {
                Icon(
                    imageVector = Icons.Rounded.PhotoCamera,
                    contentDescription = "Take picture",
                    tint = Color.Green,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(1.dp)
                )
            }
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun CameraViewPreview() {
    CameraView({})
}
