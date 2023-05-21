package com.d3st.e_coding.ui.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.d3st.e_coding.R
import com.d3st.e_coding.presentation.theme.*

@Composable
fun RatingElement(
    modifier: Modifier = Modifier,
    rating: Int = 0
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(R.string.danger))
        RatingBar(rating = rating)
    }

}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int = 0
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
    Row(
        modifier = modifier
            .padding(all = 3.dp)
            .size(width = 100.dp, height = 60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) { x ->
            if (x < rating) {
                Card(
                    modifier = Modifier.size(width = 15.dp, height = 50.dp),
                    //     .padding(all = 2.dp),
                    backgroundColor = backgroundColor,
                    border = BorderStroke(1.dp, Grey700),
                    elevation = 5.dp,
                ) {

                }
            } else {
                Card(
                    modifier = Modifier.size(width = 15.dp, height = 50.dp),
                    //.padding(all = 2.dp),
                    backgroundColor = Grey100,
                    border = BorderStroke(1.dp, Grey300),
                    elevation = 3.dp,
                ) {

                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun RatingElementPreview(
) {
    EcodingTheme {
        RatingElement(rating = 3)
    }
}

@Preview(showBackground = true)
@Composable
fun RatingBarPreview(
    @PreviewParameter(RatingBarPreviewProvider::class)
    rating: Int
) {
    EcodingTheme {
        RatingBar(rating = rating)
    }
}

class RatingBarPreviewProvider : PreviewParameterProvider<Int> {
    override val values: Sequence<Int>
        get() = sequenceOf(0, 1, 2, 3, 4, 5)
}