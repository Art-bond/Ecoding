package com.d3st.e_coding.ui.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.d3st.e_coding.presentation.theme.*


@Composable
fun AdditiveCard(
    modifier: Modifier = Modifier,
    text: String,
    rating: Int
) {
    val backgroundColor = when (rating) {
        0 -> LightGreenA400
        1 -> LimeA400
        2 -> YellowA400
        3 -> AmberA400
        4 -> DeepOrangeA400
        5 -> RedA700
        else -> Grey100
    }

    Card(
        modifier = modifier
            .size(width = 100.dp, height = 60.dp)
            .fillMaxSize(), backgroundColor = backgroundColor
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(fraction = .1f)
        ) {
            Text(
                modifier = Modifier.fillMaxSize(1f)
                //.padding(all = 2.dp)
                ,
                text = text,
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold

            )
        }
    }

}


@Preview
@Composable
fun AdditiveCardPreviewLong() {
    EcodingTheme {
        AdditiveCard(text = "E2122", rating = 1)
    }
}

@Preview
@Composable
fun AdditiveCardPreviewShort() {
    EcodingTheme {
        AdditiveCard(text = "E212", rating = 4)
    }
}