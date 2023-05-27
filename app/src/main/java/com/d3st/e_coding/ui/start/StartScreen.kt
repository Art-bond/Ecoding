package com.d3st.e_coding.ui.start

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Camera
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
import com.d3st.e_coding.ui.components.PressIconButton


@Composable
fun StartScreen(
    viewModel: StartScreenViewModel = viewModel(),
    onTakePhoto: (ImageCapture) -> Unit,
    onImageCropped: (ImageBitmap) -> Unit,
    onFailedCropImage: () -> Unit,
    onRecognizingText: (Uri) -> Unit,
    onExitPressed: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState()
    val backHandlingEnabled by remember { mutableStateOf(true) }
    BackHandler(backHandlingEnabled) {
        if (uiState.value == StartNavigationState.Start){
            onExitPressed()
        } else {
            viewModel.update(StartNavigationState.Start)
        }
    }
    when (uiState.value) {
        StartNavigationState.Start -> {
            StartScreenButtons(
                goToCatalog = { viewModel.update(StartNavigationState.Compendium) },
                goToCamera = { viewModel.update(StartNavigationState.Recognize) },
            )
        }
        StartNavigationState.Recognize -> {
            val backHandlingEnabled by remember { mutableStateOf(true) }
            BackHandler(backHandlingEnabled) {
                viewModel.update(StartNavigationState.Start)
            }
            RecognizeNavHost(
                viewModel = viewModel(),
                onTakePhoto = onTakePhoto,
                onImageCropped = onImageCropped,
                onFailedCropImage = onFailedCropImage,
                onRecognizingText = onRecognizingText
            )
        }
        StartNavigationState.Compendium -> {
            val backHandlingEnabled by remember { mutableStateOf(true) }
            BackHandler(backHandlingEnabled) {
                viewModel.update(StartNavigationState.Start)
            }
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
            PressIconButton(
                onClick = goToCamera,
                icon = { Icon(Icons.Filled.Camera, contentDescription = null) },
                text = {
                    Text(
                        text = stringResource(R.string.check_compound),
                        fontSize = TextUnit(20F, TextUnitType.Sp)
                    )
                },
                Modifier.padding(bottom = 8.dp)
            )
            PressIconButton(
                onClick = goToCatalog,
                icon = { Icon(Icons.Filled.Book, contentDescription = null) },
                text = {
                    Text(
                        text = stringResource(R.string.find_item),
                        fontSize = TextUnit(20F, TextUnitType.Sp)
                    )
                }
            )
        }
    }
}