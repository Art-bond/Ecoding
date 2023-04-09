package com.d3st.e_coding.ui.recognize

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Screen show recognizing text
 *
 * @param modifier view modifier
 * @param recognizingText recognizing text
 */
@Composable
fun RecognizeTextScreen(
    modifier: Modifier,
    recognizingText: String,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = modifier.padding(16.dp),
            text = recognizingText,
            )
    }
}