package com.d3st.e_coding

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.d3st.e_coding.presentation.theme.EcodingTheme
import com.d3st.e_coding.ui.MyAppNavHost
import com.d3st.e_coding.utils.getOutputDirectory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraExecutor = Executors.newSingleThreadExecutor()

        setContent {
            EcodingTheme() {
                val navController = rememberNavController()
                var uri by rememberSaveable { mutableStateOf(Uri.EMPTY) }

                MyAppNavHost(
                    navController = navController,
                    onTakePhoto = {
                        takePhoto(
                            executor = cameraExecutor,
                            imageCapture = it,
                            onImageCaptured = { imageUri ->
                                uri = imageUri
                                navigateToAfterPhotoScreen(navController)
                            },
                            onError = { message -> Log.e("TAG", "View error:", message) },
                        )
                    },
                    imageForAfterView = uri
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
        imageCapture: ImageCapture,
        onImageCaptured: (Uri) -> Unit,
        onError: (ImageCaptureException) -> Unit,
        executor: ExecutorService,
    ) {

        val photoFile = File(
            applicationContext.getOutputDirectory(),
            SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, "Take photo error:", exception)
                    onError(exception)
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Log.e(TAG, "Take photo saved: $photoFile")
                    val savedUri = Uri.fromFile(photoFile)
                    onImageCaptured(savedUri)
                }
            })
    }

    private fun navigateToAfterPhotoScreen(navController: NavController) {
        CoroutineScope(Dispatchers.Main).launch {
            navController.navigate("AfterCameraScreen")
        }
    }


    companion object {
        private const val TAG = "CameraX-MLKit"
    }
}



