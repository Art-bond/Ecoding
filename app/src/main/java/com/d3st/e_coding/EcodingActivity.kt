package com.d3st.e_coding

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.content.ContextCompat
import com.d3st.e_coding.presentation.theme.EcodingTheme
import com.d3st.e_coding.ui.camera.RecognizeViewModel
import com.d3st.e_coding.ui.start.StartScreen
import com.d3st.e_coding.utils.EcodingFireBaseAnalyzer
import com.d3st.e_coding.utils.EcodingPhotoHandler
import com.d3st.e_coding.utils.TextRecognizeResultCallback
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalGetImage
class EcodingActivity : ComponentActivity(),
    ImageCapture.OnImageSavedCallback,
    TextRecognizeResultCallback {

    private lateinit var photoHandler: EcodingPhotoHandler
    private lateinit var textAnalyzer: EcodingFireBaseAnalyzer

    private val sharedViewModel: RecognizeViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        // Request camera permissions
        if (!hasPermissions()) {
            requestPermissions()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        photoHandler = EcodingPhotoHandler(this.baseContext, this)
        textAnalyzer = EcodingFireBaseAnalyzer(this, this)

        setContent {
            EcodingTheme {
                StartScreen(
                    onTakePhoto = photoHandler::handlePhotoResult,
                    onImageCropped = ::recognizeBitmapText,
                    onFailedCropImage = ::cropError,
                    onRecognizingText = ::recognizeTextByUri,
                    onExitPressed = ::finish,
                )
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        photoHandler.clear()
    }

    private fun requestPermissions() {
        activityResultLauncher.launch(PERMISSIONS_REQUIRED)
    }

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            // Handle Permission granted/rejected
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in PERMISSIONS_REQUIRED && !it.value)
                    permissionGranted = false
                Log.d(TAG,"permissions ${it.key} - ${it.value}" )
            }
            if (!permissionGranted) {
                Log.d(TAG,"Permission request denied" )
                finish()
            }
        }

    /** Convenience method used to check if all permissions required by this app are granted */
    private fun hasPermissions() = PERMISSIONS_REQUIRED.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun recognizeBitmapText(imageBitmap: ImageBitmap) {
        val image = FirebaseVisionImage.fromBitmap(imageBitmap.asAndroidBitmap())
        textAnalyzer.recognizeText(image)
    }

    private fun recognizeTextByUri(pictureUri: Uri) {
        val image = FirebaseVisionImage.fromFilePath(this, pictureUri)
        textAnalyzer.recognizeText(image)
    }

    private fun cropError() {
        sharedViewModel.showError("crop is failed")
    }

    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
        val savedUri = outputFileResults.savedUri ?: Uri.EMPTY
        sharedViewModel.updateUri(savedUri)
    }

    override fun onError(exception: ImageCaptureException) {
        Log.e(TAG, "Photo capture failed: ${exception.message}", exception)
        exception.message?.let { sharedViewModel.showError(it) }
    }

    override fun onSuccessTextRecognize(text: FirebaseVisionText) {
        // Task completed successfully
        val resultWordList = mutableListOf<String>()
        val resultText = text.text
        for (block in text.textBlocks) {
            val blockText = block.text
            val blockConfidence = block.confidence
            val blockLanguages = block.recognizedLanguages
            val blockCornerPoints = block.cornerPoints
            val blockFrame = block.boundingBox
            for (line in block.lines) {
                val lineText = line.text
                val lineConfidence = line.confidence
                val lineLanguages = line.recognizedLanguages
                val lineCornerPoints = line.cornerPoints
                val lineFrame = line.boundingBox
                for (element in line.elements) {
                    val elementText = element.text
                    resultWordList.add(elementText)
                    val elementConfidence = element.confidence
                    val elementLanguages = element.recognizedLanguages
                    val elementCornerPoints = element.cornerPoints
                    val elementFrame = element.boundingBox
                }
            }
        }
        sharedViewModel.addRecognizedText(resultText, resultWordList)
    }

    override fun onFailureTextRecognize(e: Exception) {
        Log.e(TAG, "fail text recognize: $e")
        e.message?.let { sharedViewModel.showError(it) }
    }

    companion object {
        private const val TAG = "EcodingActivity"

        private val PERMISSIONS_REQUIRED =
            mutableListOf (
                Manifest.permission.CAMERA,
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}