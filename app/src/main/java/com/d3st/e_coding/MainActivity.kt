package com.d3st.e_coding

import android.Manifest
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.navigation.compose.rememberNavController
import com.d3st.e_coding.presentation.theme.EcodingTheme
import com.d3st.e_coding.ui.MyAppNavHost
import com.d3st.e_coding.ui.camera.CameraViewModel
import com.d3st.e_coding.utils.MediaStoreUtils
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var mediaStoreUtils: MediaStoreUtils
    private val viewModel: CameraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraExecutor = Executors.newSingleThreadExecutor()
        mediaStoreUtils = MediaStoreUtils(applicationContext)

        setContent {
            EcodingTheme {
                MyAppNavHost(
                    navController = rememberNavController(),
                    viewModel = viewModel,
                    onTakePhoto = {
                        takePhoto(
                            imageCapture = it,
                            onImageCaptured = { imageUri ->
                                viewModel.updateUri(imageUri, false)
                            },
                            onError = { message ->
                                Log.e(TAG, "View error:", message)
                                viewModel.showError(message.toString())
                            },
                        )
                    },
                )
            }
        }

        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
            } else {
                // Explain to the user that the feature is unavailable because the
                // feature requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.
                finish()
            }
        }

    private fun takePhoto(
        filenameFormat: String = "yyyy-MM-dd-HH-mm-ss-SSS",
        imageCapture: ImageCapture?,
        onImageCaptured: (Uri) -> Unit,
        onError: (ImageCaptureException) -> Unit,
    ) {
        imageCapture ?: return

        // Create time stamped name and MediaStore entry.
        val name = SimpleDateFormat(filenameFormat, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        imageCapture.takePicture(
            outputOptions,
            cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, "Take photo error:", exception)
                    onError(exception)
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = outputFileResults.savedUri ?: Uri.EMPTY
                    Log.d(TAG, "Photo capture succeeded: $savedUri")
                    onImageCaptured(savedUri)
                }
            })
    }

    companion object {
        private const val TAG = "CameraX-MLKit"
    }
}



