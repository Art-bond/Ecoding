package com.d3st.e_coding.ui.camera

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d3st.e_coding.data.foodadditivesrepository.AppContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for CameraScreen
 */
class CameraViewModel(
    private val appContainer: AppContainer
) : ViewModel() {

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

    /**
     *
     */
    fun addRecognizedText(text: String, words: List<String>){
        viewModelScope.launch {
            val additives = appContainer.foodAdditivesRepository.getAdditivesByNames(words)
            _uiState.update { currentState ->
                currentState.copy(
                    recognizedText = additives.map { it.name }.toString(),
                )
            }
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