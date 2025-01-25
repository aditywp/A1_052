package com.example.coba_manajemen.viewmodel.proyek

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coba_manajemen.model.Proyek
import com.example.coba_manajemen.repository.ProyekRepository
import kotlinx.coroutines.launch

class InsertProyekViewModel (
    private val pryk: ProyekRepository
): ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertPrykState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertPryk() {
        viewModelScope.launch {
            try {
                pryk.insertProyek(uiState.insertUiEvent.toPryk())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val idProyek: Int = 0,
    val namaProyek: String = "",
    val deskripsiProyek: String = "",
    val tanggalMulai: String = "",
    val tanggalBerakhir: String = "",
    val statusProyek: String = ""
)

fun InsertUiEvent.toPryk(): Proyek = Proyek(
    idProyek = idProyek,
    namaProyek = namaProyek,
    deskripsiProyek = deskripsiProyek,
    tanggalMulai = tanggalMulai,
    tanggalBerakhir = tanggalBerakhir,
    statusProyek = statusProyek
)

fun Proyek.toUiStatePryk(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Proyek.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idProyek = idProyek,
    namaProyek = namaProyek,
    deskripsiProyek = deskripsiProyek,
    tanggalMulai = tanggalMulai,
    tanggalBerakhir = tanggalBerakhir,
    statusProyek = statusProyek
)