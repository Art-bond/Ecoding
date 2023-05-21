package com.d3st.e_coding.ui.compendium

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d3st.e_coding.data.foodadditivesrepository.IFoodAdditivesRepository
import com.d3st.e_coding.ui.details.DetailsFoodAdditiveDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompendiumViewModel @Inject constructor(private val repository: IFoodAdditivesRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(CompendiumUiState())
    val uiState: StateFlow<CompendiumUiState>
        get() = _uiState

    fun getAllAdditives() {
        viewModelScope.launch {
            _uiState.update { currentState -> currentState.copy(additives = repository.getAll()) }
        }
    }

    fun getByName(name: String): DetailsFoodAdditiveDomainModel {
        return uiState.value.additives!!.first { x -> x.canonicalName == name }
    }


}

data class CompendiumUiState(
    val additives: List<DetailsFoodAdditiveDomainModel>? = null
)