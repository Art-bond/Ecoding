package com.d3st.e_coding.ui.camera

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for CameraScreen
 */
class CameraViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(CameraUiState(Uri.EMPTY))
    val uiState: StateFlow<CameraUiState>
        get() = _uiState


    /**
     * Update camera state
     */
    fun updateUri(imageUri: Uri, goToCameraScreen: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(snapshotUri = imageUri, isCameraRun = goToCameraScreen)
        }
    }

    fun showError(message: String) {
        _uiState.update { currentState ->
            currentState.copy(
                errorMessage = message
            )
        }
    }
}

data class CameraUiState(
    val snapshotUri: Uri,
    val isCameraRun: Boolean = true,
    val errorMessage: String? = null,
)