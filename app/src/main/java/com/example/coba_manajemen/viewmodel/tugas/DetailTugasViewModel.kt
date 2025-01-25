package com.example.coba_manajemen.viewmodel.tugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coba_manajemen.model.Proyek
import com.example.coba_manajemen.model.Tim
import com.example.coba_manajemen.model.Tugas
import com.example.coba_manajemen.repository.ProyekRepository
import com.example.coba_manajemen.repository.TimRepository
import com.example.coba_manajemen.repository.TugasRepository
import com.example.coba_manajemen.view.tugas.DestinasiTugasDetail
import kotlinx.coroutines.launch


sealed class DetailTugasUiState {
    data class Success(val tugas: Tugas) : DetailTugasUiState()
    object Error : DetailTugasUiState()
    object Loading : DetailTugasUiState()
}

class DetailTugasViewModel(
    savedStateHandle: SavedStateHandle,
    private val tgs: TugasRepository,
    private val tim: TimRepository,
    private val pryk: ProyekRepository
): ViewModel(){

    var tgsDetailState: DetailTugasUiState by mutableStateOf(DetailTugasUiState.Loading)
        private set

    private val _idTugas: String = checkNotNull(savedStateHandle[DestinasiTugasDetail.IDTUGAS])

    init {
        getTugasbyId()
    }

    //Relasi Tim, Proyek ,tgs
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

    fun getTugasbyId() {
        viewModelScope.launch {
            tgsDetailState = DetailTugasUiState.Loading
            tgsDetailState = try {
                val tgs = tgs.getTugasById(_idTugas.toInt())
                DetailTugasUiState.Success(tgs)
            } catch (e: Exception) {
                DetailTugasUiState.Error
            } catch (e: Exception) {
                DetailTugasUiState.Error
            }
        }
    }
}