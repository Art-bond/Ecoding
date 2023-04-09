package com.d3st.e_coding.ui

import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.d3st.e_coding.ui.camera.CameraScreen
import com.d3st.e_coding.ui.camera.CameraViewModel
import com.d3st.e_coding.ui.camera.SnapshotScreen
import com.d3st.e_coding.ui.recognize.RecognizeTextScreen

/**
 * Navigation Host
 *
 * @param modifier Модификатор вида
 * @param navController навигатор
 * @param startDestination начальный экран
 * @param onTakePhoto результат сделанной фото
 */
@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    viewModel: CameraViewModel,
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    startDestination: String = AppScreens.PHOTO.value,
    onTakePhoto: (ImageCapture) -> Unit,
    onRecognizingText: (Uri) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                    imageFile = uiState.snapshotUri,
                    onClickNext = {
                        onRecognizingText(uiState.snapshotUri)
                    }
                )
            }
            composable(AppScreens.RECOGNIZED.value) {
                RecognizeTextScreen(
                    modifier = modifier,
                    recognizingText = uiState.recognizedText.orEmpty()
                )
            }
        }
    }
    LaunchedEffect(uiState.nextScreen.value) {
        navController.navigate(uiState.nextScreen.value)
    }
}
enum class AppScreens(val value: String) {
    START("StartScreen"),
    PHOTO("CameraScreen"),
    POST_VIEW_PHOTO("SnapshotScreen"),
    RECOGNIZED("RecognizeTextScreen")
}


