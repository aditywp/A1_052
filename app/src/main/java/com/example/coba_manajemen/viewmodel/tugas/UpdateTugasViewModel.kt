package com.example.coba_manajemen.viewmodel.tugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coba_manajemen.model.Proyek
import com.example.coba_manajemen.model.Tim
import com.example.coba_manajemen.repository.ProyekRepository
import com.example.coba_manajemen.repository.TimRepository
import com.example.coba_manajemen.repository.TugasRepository
import com.example.coba_manajemen.view.tugas.DestinasiTugasUpdate
import kotlinx.coroutines.launch


class UpdateTugasViewModel (
    savedStateHandle: SavedStateHandle,
    private val tgs: TugasRepository,
    private val tim: TimRepository,
    private val pryk: ProyekRepository
): ViewModel(){
    var updatedTugasUiState by mutableStateOf(InsertTugasUiState())
        private set

    var timList by mutableStateOf<List<Tim>>(emptyList())
    var prykList by mutableStateOf<List<Proyek>>(emptyList())

    init {
        loadTimDanProyek()

    }

    private fun loadTimDanProyek() {
        viewModelScope.launch {
            try {
                timList = tim.getAllTim().data
                prykList = pryk.getAllProyek().data
            }catch (e: Exception) {

            }
        }
    }


    private val _idTugas: Int = checkNotNull(savedStateHandle[DestinasiTugasUpdate.IDTUGAS])

    init {
        viewModelScope.launch {
            updatedTugasUiState = tgs.getTugasById(_idTugas)
                .toUiStateTugas()
        }
    }

    fun updateInsertTugasState(InsertTugasUiEvent: InsertTugasUiEvent) {
        updatedTugasUiState = InsertTugasUiState(insertTugasUiEvent = InsertTugasUiEvent)
    }

    suspend fun updateTugas() {
        viewModelScope.launch {
            try {
                tgs.updateTugas(_idTugas, updatedTugasUiState.insertTugasUiEvent.toTgs())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}