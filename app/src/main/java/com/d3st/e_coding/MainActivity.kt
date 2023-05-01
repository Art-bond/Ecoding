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
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.navigation.compose.rememberNavController
import com.d3st.e_coding.data.foodadditivesrepository.AppContainer
import com.d3st.e_coding.presentation.theme.EcodingTheme
import com.d3st.e_coding.ui.MyAppNavHost
import com.d3st.e_coding.ui.camera.CameraViewModel
import com.d3st.e_coding.utils.MediaStoreUtils
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var mediaStoreUtils: MediaStoreUtils
    private lateinit var appContainer: AppContainer
    private lateinit var viewModel: CameraViewModel
    //private val viewModel: CameraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (application as EcodingApplication).container
        viewModel = CameraViewModel(appContainer)
        cameraExecutor = Executors.newSingleThreadExecutor()
        mediaStoreUtils = MediaStoreUtils(applicationContext)

        setContent {
            EcodingTheme {
                MyAppNavHost(
                    navController = rememberNavController(),
                    viewModel = viewModel,
                    onTakePhoto = ::takePhoto,
                    onImageCropped = ::recognizeBitmapText,
                    onFailedCropImage = ::cropError,
                    onRecognizingText = ::recognizeTextByUri
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
        imageCapture: ImageCapture?,
    ) {
        imageCapture ?: return

        // Create time stamped name and MediaStore entry.
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
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
                    exception.message?.let { viewModel.showError(it) }
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = outputFileResults.savedUri ?: Uri.EMPTY
                    Log.d(TAG, "Photo capture succeeded: $savedUri")
                    viewModel.updateUri(savedUri)
                }
            })
    }

    private fun recognizeBitmapText(imageBitmap: ImageBitmap) {
        val image = InputImage.fromBitmap(imageBitmap.asAndroidBitmap(), 0)
        recognizeText(image)
    }

    private fun recognizeTextByUri(pictureUri: Uri) {
        val image = InputImage.fromFilePath(this, pictureUri)
        recognizeText(image)
    }

    private fun recognizeText(image: InputImage) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(image)
            .addOnSuccessListener { result ->
                val resultWordList = mutableListOf<String>()
                val resultText = result.text
                for (block in result.textBlocks) {
                    val blockText = block.text
                    val blockCornerPoints = block.cornerPoints
                    val blockFrame = block.boundingBox
                    for (line in block.lines) {
                        val lineText = line.text
                        val lineCornerPoints = line.cornerPoints
                        val lineFrame = line.boundingBox
                        for (element in line.elements) {
                            val elementText = element.text
                            resultWordList.add(elementText)
                            val elementCornerPoints = element.cornerPoints
                            val elementFrame = element.boundingBox
                        }
                    }
                }
                viewModel.addRecognizedText(resultText, resultWordList)
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "fail recognize: $e")
                e.message?.let { viewModel.showError(it) }
            }
    }

    private fun cropError() {
        viewModel.showError("crop is failed")
    }

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

        private const val TAG = "CameraX-MLKit"
    }
}