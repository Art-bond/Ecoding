package com.d3st.e_coding.ui.recognize

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d3st.e_coding.presentation.theme.EcodingTheme

/**
 * Screen show recognizing text
 *
 * @param modifier view modifier
 * @param recognizingText recognizing text
 */
@Composable
fun RecognizeTextScreen(
    sourceEcodeText: String,
    recognizingText: String,
    modifier: Modifier = Modifier,
    ) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier.padding(16.dp),
            text = sourceEcodeText,
        )
        Text(
            modifier = modifier.padding(16.dp),
            text = recognizingText,
            )
    }
}

@Preview
@Composable
fun RecognizeTextScreenPreview(){
    EcodingTheme {
        RecognizeTextScreen(sourceEcodeText = "source", recognizingText = "result")
    }
}