package com.example.coba_manajemen.viewmodel.anggotatim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coba_manajemen.model.AnggotaTim
import com.example.coba_manajemen.model.Tim
import com.example.coba_manajemen.repository.AnggotaTimRepository
import com.example.coba_manajemen.repository.TimRepository
import kotlinx.coroutines.launch

class InsertAnggotaTimViewModel (
    private val agt: AnggotaTimRepository,
    private val tim: TimRepository
): ViewModel() {
    var agtuiState by mutableStateOf(InsertAnggotaTimUiState())
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

    fun updateInsertAnggotaTimState(insertAnggotaTimUiEvent: InsertAnggotaTimUiEvent) {
        agtuiState = InsertAnggotaTimUiState(insertAnggotaTimUiEvent = insertAnggotaTimUiEvent)
    }

    suspend fun insertAnggotaTim() {
        viewModelScope.launch {
            try {
                agt.insertAnggotaTim(agtuiState.insertAnggotaTimUiEvent.toagt())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class  InsertAnggotaTimUiState(
    val insertAnggotaTimUiEvent: InsertAnggotaTimUiEvent = InsertAnggotaTimUiEvent()
)

data class InsertAnggotaTimUiEvent(
    val idAnggota: Int = 0,
    val idTim: Int = 0,
    val namaAnggota: String = "",
    val peran: String = ""
)

fun InsertAnggotaTimUiEvent.toagt(): AnggotaTim = AnggotaTim(
    idAnggota = idAnggota,
    idTim = idTim,
    namaAnggota = namaAnggota,
    peran = peran
)

fun AnggotaTim.toUiStateAnggotaTim(): InsertAnggotaTimUiState = InsertAnggotaTimUiState(
    insertAnggotaTimUiEvent = toinsertAnggotaTimUiEvent()
)

fun AnggotaTim.toinsertAnggotaTimUiEvent(): InsertAnggotaTimUiEvent = InsertAnggotaTimUiEvent(
    idAnggota = idAnggota,
    idTim = idTim,
    namaAnggota = namaAnggota,
    peran = peran
)