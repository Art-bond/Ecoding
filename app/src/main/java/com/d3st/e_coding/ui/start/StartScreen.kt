package com.d3st.e_coding.ui.start

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.d3st.e_coding.R
import com.d3st.e_coding.ui.CompendiumNavHost
import com.d3st.e_coding.ui.RecognizeNavHost

@Composable
fun StartScreen(
    viewModel: StartScreenViewModel = viewModel(),
    onTakePhoto: (ImageCapture) -> Unit,
    onImageCropped: (ImageBitmap) -> Unit,
    onFailedCropImage: () -> Unit,
    onRecognizingText: (Uri) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState()

    val backHandlingEnabled by remember { mutableStateOf(true) }
    BackHandler(backHandlingEnabled) {
        viewModel.update(StartNavigationState.Start)
    }

    when (uiState.value) {
        StartNavigationState.Start -> {
            StartScreenButtons(
                goToCatalog = { viewModel.update(StartNavigationState.Compendium) },
                goToCamera = { viewModel.update(StartNavigationState.Recognize) },

            )
        }
        StartNavigationState.Recognize -> {
            RecognizeNavHost(
                viewModel = viewModel(),
                onTakePhoto = onTakePhoto,
                onImageCropped = onImageCropped,
                onFailedCropImage = onFailedCropImage,
                onRecognizingText = onRecognizingText
            )
        }
        StartNavigationState.Compendium -> {
            CompendiumNavHost()
        }
    }
}


@Composable
fun StartScreenButtons(
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