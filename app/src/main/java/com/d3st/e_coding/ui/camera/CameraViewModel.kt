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

    private val _uiState = MutableStateFlow(CameraUiState())
    val uiState: StateFlow<CameraUiState>
        get() = _uiState


    /**
     * Update camera state
     */
    fun updateUri(imageUri: Uri) {
        _uiState.update { currentState ->
            currentState.copy(snapshotUri = imageUri, recognizedText = null)
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
            )
        }
    }

    /**
     * Reset camera snapshot and recognized text
     */
    fun reset(){
        _uiState.update { currentState ->
            currentState.copy(
                snapshotUri = null,
                recognizedText = null
            )
        }
    }
}

data class CameraUiState(
    val snapshotUri: Uri? = null,
    val errorMessage: String? = null,
    val recognizedText: String? = null
)