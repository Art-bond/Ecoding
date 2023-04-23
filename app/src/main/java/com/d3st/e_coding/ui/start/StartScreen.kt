package com.d3st.e_coding.ui.start

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.d3st.e_coding.R
import com.d3st.e_coding.presentation.theme.EcodingTheme

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    goToCamera: () -> Unit,
    goToCatalog: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = modifier.padding(bottom = 20.dp),
                onClick = goToCamera
            ) {
                Text(
                    text = stringResource(R.string.check_compound),
                    fontSize = TextUnit(20F, TextUnitType.Sp)
                )
            }
            Button(
                onClick = goToCatalog
            ) {
                Text(
                    text = stringResource(R.string.find_item),
                    fontSize = TextUnit(20F, TextUnitType.Sp)

                )

            }
        }
    }
}

@Preview
@Composable
fun StartScreenPreview() {
    EcodingTheme {
        StartScreen(
            goToCamera = {},
            goToCatalog = {}
        )
    }
}