package com.example.coba_manajemen.viewmodel.anggotatim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coba_manajemen.model.Tim
import com.example.coba_manajemen.repository.AnggotaTimRepository
import com.example.coba_manajemen.repository.TimRepository
import com.example.coba_manajemen.view.anggotatim.DestinasiAnggotaUpdate
import kotlinx.coroutines.launch


class UpdateAnggotaTimViewModel (
    savedStateHandle: SavedStateHandle,
    private val agt: AnggotaTimRepository,
    private val tim: TimRepository

): ViewModel(){
    var updatedAnggotaTimUiState by mutableStateOf(InsertAnggotaTimUiState())
        private set

    var timList by mutableStateOf<List<Tim>>(emptyList())

    init {
        loadTim()
    }

    private fun loadTim() {
        viewModelScope.launch {
            try {
                timList = tim.getAllTim().data
            }catch (e: Exception) {

            }
        }
    }

    private val _idAnggota: Int = checkNotNull(savedStateHandle[DestinasiAnggotaUpdate.IDANGGOTA])

    init {
        viewModelScope.launch {
            updatedAnggotaTimUiState = agt.getAnggotaTimById(_idAnggota)
                .toUiStateAnggotaTim()
        }
    }

    fun updateInsertAnggotaTimState(InsertAnggotaTimUiEvent: InsertAnggotaTimUiEvent) {
        updatedAnggotaTimUiState = InsertAnggotaTimUiState(insertAnggotaTimUiEvent = InsertAnggotaTimUiEvent)
    }

    suspend fun updateAnggotaTim() {
        viewModelScope.launch {
            try {
                agt.updateAnggotaTim(_idAnggota, updatedAnggotaTimUiState.insertAnggotaTimUiEvent.toagt())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}