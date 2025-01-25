package com.example.coba_manajemen.viewmodel.proyek

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coba_manajemen.model.Proyek
import com.example.coba_manajemen.repository.ProyekRepository
import com.example.coba_manajemen.view.proyek.DestinasiProyekDetail
import kotlinx.coroutines.launch

sealed class DetailUiState {
    data class Success(val proyek: Proyek) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailProyekViewModel(
    savedStateHandle: SavedStateHandle,
    private val pryk: ProyekRepository
): ViewModel(){

    var proyekDetailState: DetailUiState by mutableStateOf(
        DetailUiState.Loading
    )
        private set

    private val _idProyek: String = checkNotNull(savedStateHandle[DestinasiProyekDetail.IDPROYEK])

    init {
        getProyekbyId()
    }

    fun getProyekbyId() {
        viewModelScope.launch {
            proyekDetailState =
                DetailUiState.Loading
            proyekDetailState = try {
                val proyek = pryk.getProyekById(_idProyek.toInt())
                DetailUiState.Success(proyek)
            } catch (e: Exception) {
                DetailUiState.Error
            } catch (e: Exception) {
                DetailUiState.Error
            }
        }
    }
}