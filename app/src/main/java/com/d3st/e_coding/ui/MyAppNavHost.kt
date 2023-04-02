package com.d3st.e_coding.ui

import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.d3st.e_coding.ui.camera.CameraAfterScreen
import com.d3st.e_coding.ui.camera.CameraScreen

/**
 * Navigation Host
 *
 * @param modifier Модификатор вида
 * @param navController навигатор
 * @param startDestination начальный экран
 * @param onTakePhoto результат сделанной фото
 * @param imageForAfterView место нахождения фото
 */
@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppScreens.PHOTO.value,
    onTakePhoto: (ImageCapture) -> Unit,
    imageForAfterView: Uri = Uri.EMPTY,
) {


    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        NavHost(navController = navController, startDestination = startDestination) {
            composable(AppScreens.START.value) {

            }
            composable(AppScreens.PHOTO.value) {
                CameraScreen(onTakePhoto = onTakePhoto)
            }
            composable(AppScreens.PREVIEW_PHOTO.value) {
                CameraAfterScreen(
                    imageFile = imageForAfterView,
                    onClickNext = {}
                )
            }
        }

    }
}

enum class AppScreens(val value: String) {
    START("StartScreen"),
    PHOTO("CameraScreen"),
    PREVIEW_PHOTO("AfterCameraScreen")
}


