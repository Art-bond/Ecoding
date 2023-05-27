package com.d3st.e_coding.ui

import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.d3st.e_coding.ui.camera.CameraScreen
import com.d3st.e_coding.ui.camera.CameraUiState
import com.d3st.e_coding.ui.camera.RecognizeViewModel
import com.d3st.e_coding.ui.camera.SnapshotScreen
import com.d3st.e_coding.ui.recognize.RecognizeTextScreen

/**
 * Navigation Host
 *
 * @param modifier Модификатор вида
 * @param navController навигатор
 * @param startDestination начальный экран
 * @param onTakePhoto результат фотографирования
 * @param onImageCropped результат подрезки изображения
 * @param onFailedCropImage неудача подрезки изображения
 * @param onRecognizingText результат распознавания текста
 * @param snackbarHostState
 * @param viewModel viewModel [RecognizeViewModel]
 */
@Composable
fun RecognizeNavHost(
    modifier: Modifier = Modifier,
    viewModel: RecognizeViewModel,
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    startDestination: String = AppScreens.PHOTO.value,
    onTakePhoto: (ImageCapture) -> Unit,
    onImageCropped: (ImageBitmap) -> Unit,
    onFailedCropImage: () -> Unit,
    onRecognizingText: (Uri) -> Unit,
) {

    val uiState: CameraUiState by viewModel.uiState.collectAsStateWithLifecycle()

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = modifier.fillMaxSize(),
        // color = MaterialTheme.colors.background,
    ) {
        NavHost(navController = navController, startDestination = startDestination) {
            composable(AppScreens.PHOTO.value) {
                CameraScreen(
                    onTakePhoto = onTakePhoto,
                )
            }
            composable(AppScreens.POST_VIEW_PHOTO.value) {
                SnapshotScreen(
                    imageFile = uiState.snapshotUri ?: Uri.EMPTY,
                    onClickNext = {
                        onRecognizingText(uiState.snapshotUri ?: Uri.EMPTY)
                    },
                    onImageCropped = onImageCropped,
                    onFailedToLoadImage = onFailedCropImage
                )
            }
            composable(AppScreens.RECOGNIZED.value) {
                RecognizeTextScreen(
                    recognizingText = uiState.recognizedText.orEmpty(),
                    sourceEcodeText = uiState.sourceTextForRecognize.orEmpty()
                )
            }
        }
    }

    when {
        uiState.recognizedText != null -> navController.navigate(AppScreens.RECOGNIZED.value) {
            popUpTo(AppScreens.POST_VIEW_PHOTO.value) { inclusive = true }
        }
        uiState.snapshotUri != null -> navController.navigate(AppScreens.POST_VIEW_PHOTO.value)
    }
}

enum class AppScreens(val value: String) {
    PHOTO("CameraScreen"),
    POST_VIEW_PHOTO("SnapshotScreen"),
    RECOGNIZED("RecognizeTextScreen"),
    COMPENDIUM("Compendium"),
    DETAILS("Details")
}