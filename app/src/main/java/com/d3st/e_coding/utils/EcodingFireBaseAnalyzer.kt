package com.d3st.e_coding.utils

import android.content.Context
import android.util.SparseIntArray
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions

/**
 * FireBase Text Recognizer
 */
@ExperimentalGetImage
class EcodingFireBaseAnalyzer(
    private val context: Context,
    private val callback: TextRecognizeResultCallback,
) : ImageAnalysis.Analyzer {

    private val orientations: SparseIntArray = SparseIntArray()

    private fun degreesToFirebaseRotation(degrees: Int): Int = when (degrees) {
        0 -> FirebaseVisionImageMetadata.ROTATION_0
        90 -> FirebaseVisionImageMetadata.ROTATION_90
        180 -> FirebaseVisionImageMetadata.ROTATION_180
        270 -> FirebaseVisionImageMetadata.ROTATION_270
        else -> throw Exception("Rotation must be 0, 90, 180, or 270.")
    }

    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image

        val imageRotation =
            degreesToFirebaseRotation(orientations.get(imageProxy.imageInfo.rotationDegrees))
        if (mediaImage != null) {
            //val image = FirebaseVisionImage.fromMediaImage(mediaImage, imageRotation)
            val image: FirebaseVisionImage =
                FirebaseVisionImage.fromMediaImage(mediaImage, imageRotation)
            recognizeText(image)
        }
    }

    fun recognizeText(image: FirebaseVisionImage) {

        val options = FirebaseVisionCloudTextRecognizerOptions.Builder()
            .setLanguageHints(listOf("en", "ru"))
            .build()

        FirebaseApp.initializeApp(context)?.let {
            val detector = FirebaseVision.getInstance(it).getCloudTextRecognizer(options)

            detector.processImage(image)
                .addOnSuccessListener { firebaseVisionText ->
                    callback.onSuccessTextRecognize(firebaseVisionText)
                }
                .addOnFailureListener { e: Exception ->
                    callback.onFailureTextRecognize(e)
                }
        }
    }
}