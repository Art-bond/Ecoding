package com.d3st.e_coding.utils

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.ERROR_UNKNOWN
import androidx.camera.core.ImageCaptureException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

/**
 * Helper for handling photo form camera
 */
class EcodingPhotoHandler(
    val context: Context,
    private val callback: ImageCapture.OnImageSavedCallback,
) {
    private val executor = Executors.newSingleThreadExecutor()

    fun handlePhotoResult(
        imageCapture: ImageCapture?,
    ) {
        imageCapture ?: callback.onError(
            ImageCaptureException(
                ERROR_UNKNOWN,
                "imageCapture is null",
                null
            )
        )

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
        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
            .build()

        imageCapture?.takePicture(
            outputOptions,
            Executors.newSingleThreadExecutor(),
            callback
        )
    }

    fun clear() {
        executor.shutdown()
    }

    companion object {
        private const val TAG = "EcodingPhotoHandler"

        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}