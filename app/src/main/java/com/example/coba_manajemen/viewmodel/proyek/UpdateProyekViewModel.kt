package com.example.coba_manajemen.viewmodel.proyek

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coba_manajemen.repository.ProyekRepository
import com.example.coba_manajemen.view.proyek.DestinasiProyekUpdate
import kotlinx.coroutines.launch

class UpdateProyekViewModel (
    savedStateHandle: SavedStateHandle,
    private val pryk: ProyekRepository
): ViewModel(){
    var updatedUiState by mutableStateOf(InsertUiState())
        private set

    private val _idProyek: Int = checkNotNull(savedStateHandle[DestinasiProyekUpdate.IDPROYEK])

    init {
        viewModelScope.launch {
            updatedUiState = pryk.getProyekById(_idProyek)
                .toUiStatePryk()
        }
    }

    fun updateInsertPrykState(insertUiEvent: InsertUiEvent) {
        updatedUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updatePryk() {
        viewModelScope.launch {
            try {
                pryk.updateProyek(_idProyek, updatedUiState.insertUiEvent.toPryk())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}