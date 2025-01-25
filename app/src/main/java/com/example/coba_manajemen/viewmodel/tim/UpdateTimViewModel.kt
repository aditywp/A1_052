package com.example.coba_manajemen.viewmodel.tim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coba_manajemen.repository.TimRepository
import com.example.coba_manajemen.view.tim.DestinasiTimUpdate
import kotlinx.coroutines.launch

class UpdateTimViewModel (
    savedStateHandle: SavedStateHandle,
    private val tim: TimRepository
): ViewModel(){
    var updatedTimUiState by mutableStateOf(InsertTimUiState())
        private set

    private val _idTim: Int = checkNotNull(savedStateHandle[DestinasiTimUpdate.IDTIM])

    init {
        viewModelScope.launch {
            updatedTimUiState = tim.getTimById(_idTim)
                .toUiStateTim()
        }
    }

    fun updateInsertTimState(InsertTimUiEvent: InsertTimUiEvent) {
        updatedTimUiState = InsertTimUiState(insertTimUiEvent = InsertTimUiEvent)
    }

    suspend fun updateTim() {
        viewModelScope.launch {
            try {
                tim.updateTim(_idTim, updatedTimUiState.insertTimUiEvent.toTim())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}