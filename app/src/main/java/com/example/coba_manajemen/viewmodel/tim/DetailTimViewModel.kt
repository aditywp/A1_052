package com.example.coba_manajemen.viewmodel.tim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coba_manajemen.model.Tim
import com.example.coba_manajemen.repository.TimRepository
import com.example.coba_manajemen.view.tim.DestinasiTimDetail
import kotlinx.coroutines.launch


sealed class DetailTimUiState {
    data class Success(val tim: Tim) : DetailTimUiState()
    object Error : DetailTimUiState()
    object Loading : DetailTimUiState()
}

class DetailTimViewModel(
    savedStateHandle: SavedStateHandle,
    private val tim: TimRepository
): ViewModel(){

    var timDetailState: DetailTimUiState by mutableStateOf(DetailTimUiState.Loading)
        private set

    private val _idTim: String = checkNotNull(savedStateHandle[DestinasiTimDetail.IDTIM])

    init {
        getTimbyId()
    }

    fun getTimbyId() {
        viewModelScope.launch {
            timDetailState = DetailTimUiState.Loading
            timDetailState = try {
                val tim = tim.getTimById(_idTim.toInt())
                DetailTimUiState.Success(tim)
            } catch (e: Exception) {
                DetailTimUiState.Error
            } catch (e: Exception) {
                DetailTimUiState.Error
            }
        }
    }
}