package com.example.coba_manajemen.viewmodel.tim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coba_manajemen.model.Tim
import com.example.coba_manajemen.repository.TimRepository
import kotlinx.coroutines.launch

class InsertTimViewModel (
    private val tim: TimRepository
): ViewModel() {
    var uiTimState by mutableStateOf(InsertTimUiState())
        private set

    fun updateInsertTimState(insertTimUiEvent: InsertTimUiEvent) {
        uiTimState = InsertTimUiState(insertTimUiEvent = insertTimUiEvent)
    }

    suspend fun insertTim() {
        viewModelScope.launch {
            try {
                tim.insertTim(uiTimState.insertTimUiEvent.toTim())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class  InsertTimUiState(
    val insertTimUiEvent: InsertTimUiEvent = InsertTimUiEvent()
)

data class InsertTimUiEvent(
    val idTim: Int = 0,
    val namaTim: String = "",
    val deskripsiTim: String = ""
)

fun InsertTimUiEvent.toTim(): Tim = Tim(
    idTim = idTim,
    namaTim = namaTim,
    deskripsiTim = deskripsiTim
)

fun Tim.toUiStateTim(): InsertTimUiState = InsertTimUiState(
    insertTimUiEvent = toInsertTimUiEvent()
)

fun Tim.toInsertTimUiEvent(): InsertTimUiEvent = InsertTimUiEvent(
    idTim = idTim,
    namaTim = namaTim,
    deskripsiTim = deskripsiTim
)