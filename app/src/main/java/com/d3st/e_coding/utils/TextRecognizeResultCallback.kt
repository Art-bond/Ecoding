package com.d3st.e_coding.utils

import com.google.firebase.ml.vision.text.FirebaseVisionText

/**
 * Result recognizing text
 */
interface TextRecognizeResultCallback {

    /**
     * Text recognize is Successful
     */
    fun onSuccessTextRecognize(text: FirebaseVisionText)

    /**
     * Text recognize with failure
     */
    fun onFailureTextRecognize(e: Exception)
}