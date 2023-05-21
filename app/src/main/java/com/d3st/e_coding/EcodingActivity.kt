package com.d3st.e_coding

import android.Manifest
import android.net.Uri
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
import androidx.navigation.compose.rememberNavController
import com.d3st.e_coding.presentation.theme.EcodingTheme
import com.d3st.e_coding.ui.RecognizeNavHost
import com.d3st.e_coding.ui.camera.RecognizeViewModel
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

    private val viewModel: RecognizeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        photoHandler = EcodingPhotoHandler(this, this)
        textAnalyzer = EcodingFireBaseAnalyzer(this, this)

        setContent {
            EcodingTheme {
                RecognizeNavHost(
                    navController = rememberNavController(),
                    viewModel = viewModel,
                    onTakePhoto = photoHandler::handlePhotoResult,
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
        photoHandler.clear()
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


    private fun recognizeBitmapText(imageBitmap: ImageBitmap) {
        val image = FirebaseVisionImage.fromBitmap(imageBitmap.asAndroidBitmap())
        textAnalyzer.recognizeText(image)
    }

    private fun recognizeTextByUri(pictureUri: Uri) {
        val image = FirebaseVisionImage.fromFilePath(this, pictureUri)
        textAnalyzer.recognizeText(image)
    }



    private fun cropError() {
        viewModel.showError("crop is failed")
    }

    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
        val savedUri = outputFileResults.savedUri ?: Uri.EMPTY
        viewModel.updateUri(savedUri)
    }

    override fun onError(exception: ImageCaptureException) {
        exception.message?.let { viewModel.showError(it) }
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
        viewModel.addRecognizedText(resultText, resultWordList)
    }

    override fun onFailureTextRecognize(e: Exception) {
        Log.e(TAG, "fail text recognize: $e")
        e.message?.let { viewModel.showError(it) }
    }

    companion object {
        private const val TAG = "EcodingActivity"
    }
}