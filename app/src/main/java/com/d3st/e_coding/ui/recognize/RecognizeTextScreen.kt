package com.d3st.e_coding.ui.recognize

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.d3st.e_coding.presentation.theme.*
import com.d3st.e_coding.ui.details.DetailsFoodAdditiveDomainModel

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
    recognizedAdditives: List<DetailsFoodAdditiveDomainModel>?,
    modifier: Modifier = Modifier,
    onAdditiveClick: (String) -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            if (recognizedAdditives != null) {
                repeat(recognizedAdditives.size) {
                    val elColor = when (recognizedAdditives[it].harmfulness) {
                        0 -> LightGreenA400
                        1 -> LimeA400
                        2 -> YellowA400
                        3 -> AmberA400
                        4 -> DeepOrangeA400
                        5 -> RedA700
                        else -> Grey100
                    }
                    Button(
                        onClick = { onAdditiveClick(recognizedAdditives[it].canonicalName) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = elColor)
                    ) {
                        Text(text = "${recognizedAdditives[it].name} - ${recognizedAdditives[it].canonicalName}")
                    }
                }
            }

        }
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
        RecognizeTextScreen(
            sourceEcodeText = "source",
            recognizingText = "result",
            onAdditiveClick = {},
            recognizedAdditives = null
        )
    }
}