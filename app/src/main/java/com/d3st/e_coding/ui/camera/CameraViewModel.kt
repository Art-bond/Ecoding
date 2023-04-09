package com.d3st.e_coding.ui.camera

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.d3st.e_coding.ui.AppScreens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for CameraScreen
 */
class CameraViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(CameraUiState(nextScreen = AppScreens.PHOTO))
    val uiState: StateFlow<CameraUiState>
        get() = _uiState


    /**
     * Update camera state
     */
    fun updateUri(imageUri: Uri) {
        _uiState.update { currentState ->
            currentState.copy(snapshotUri = imageUri, nextScreen = AppScreens.POST_VIEW_PHOTO)
        }
    }

    fun showError(message: String) {
        _uiState.update { currentState ->
            currentState.copy(
                errorMessage = message
            )
        }
    }

    fun addRecognizedText(text: String){
        _uiState.update { currentState ->
            currentState.copy(
                recognizedText = text,
                nextScreen = AppScreens.RECOGNIZED
            )
        }
    }
}

data class CameraUiState(
    val nextScreen: AppScreens = AppScreens.START,
    val snapshotUri: Uri = Uri.EMPTY,
    val errorMessage: String? = null,
    val recognizedText: String? = null
)