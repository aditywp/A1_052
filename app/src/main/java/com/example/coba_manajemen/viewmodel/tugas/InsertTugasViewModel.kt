package com.example.coba_manajemen.viewmodel.tugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coba_manajemen.model.Proyek
import com.example.coba_manajemen.model.Tim
import com.example.coba_manajemen.model.Tugas
import com.example.coba_manajemen.repository.ProyekRepository
import com.example.coba_manajemen.repository.TimRepository
import com.example.coba_manajemen.repository.TugasRepository
import kotlinx.coroutines.launch


class InsertTugasViewModel (
    private val tgs: TugasRepository,
    private val tim: TimRepository,
    private val pryk: ProyekRepository
): ViewModel() {
    var tgsuiState by mutableStateOf(InsertTugasUiState())
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

    fun updateInsertTimState(insertTugasUiEvent: InsertTugasUiEvent) {
        tgsuiState = InsertTugasUiState(insertTugasUiEvent = insertTugasUiEvent)
    }

    suspend fun insertTugas() {
        viewModelScope.launch {
            try {
                tgs.insertTugas(tgsuiState.insertTugasUiEvent.toTgs())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class  InsertTugasUiState(
    val insertTugasUiEvent: InsertTugasUiEvent = InsertTugasUiEvent()
)

data class InsertTugasUiEvent(
    val idTugas: Int = 0,
    val idTim: Int = 0,
    val idProyek: Int = 0,
    val namaTugas: String = "",
    val deskripsiTugas: String = "",
    val prioritas: String = "",
    val statusTugas: String = ""
)

fun InsertTugasUiEvent.toTgs(): Tugas = Tugas(
    idTugas = idTugas,
    idProyek = idProyek,
    idTim = idTim,
    namaTugas = namaTugas,
    deskripsiTugas = deskripsiTugas,
    prioritas = prioritas,
    statusTugas = statusTugas
)

fun Tugas.toUiStateTugas(): InsertTugasUiState = InsertTugasUiState(
    insertTugasUiEvent = toinsertTugasUiEvent()
)

fun Tugas.toinsertTugasUiEvent(): InsertTugasUiEvent = InsertTugasUiEvent(
    idTugas = idTugas,
    idProyek = idProyek,
    idTim = idTim,
    namaTugas = namaTugas,
    deskripsiTugas = deskripsiTugas,
    prioritas = prioritas,
    statusTugas = statusTugas
)