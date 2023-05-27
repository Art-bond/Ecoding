package com.d3st.e_coding.ui.start

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(): ViewModel(){

    private val _uiState = MutableStateFlow<StartNavigationState>(StartNavigationState.Start)
        val uiState = _uiState.asStateFlow()

    /**
     * Update state navigation
     */
    fun update(navigationState: StartNavigationState) {
        _uiState.update { navigationState }
    }
}