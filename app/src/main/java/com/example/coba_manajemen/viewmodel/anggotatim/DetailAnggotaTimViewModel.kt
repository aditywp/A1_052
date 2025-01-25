package com.example.coba_manajemen.viewmodel.anggotatim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coba_manajemen.model.AnggotaTim
import com.example.coba_manajemen.model.Tim
import com.example.coba_manajemen.repository.AnggotaTimRepository
import com.example.coba_manajemen.repository.TimRepository
import com.example.coba_manajemen.view.anggotatim.DestinasiAnggotaDetail
import kotlinx.coroutines.launch


sealed class DetailAnggotaTimUiState {
    data class Success(val anggota: AnggotaTim) : DetailAnggotaTimUiState()
    object Error : DetailAnggotaTimUiState()
    object Loading : DetailAnggotaTimUiState()
}

class DetailAnggotaTimViewModel(
    savedStateHandle: SavedStateHandle,
    private val agt: AnggotaTimRepository,
    private val tim: TimRepository
): ViewModel(){

    var agtDetailState: DetailAnggotaTimUiState by mutableStateOf(DetailAnggotaTimUiState.Loading)
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

    private val _idAnggota: String = checkNotNull(savedStateHandle[DestinasiAnggotaDetail.IDANGGOTA])

    init {
        getAnggotaTimbyId()
    }

    fun getAnggotaTimbyId() {
        viewModelScope.launch {
            agtDetailState = DetailAnggotaTimUiState.Loading
            agtDetailState = try {
                val agt = agt.getAnggotaTimById(_idAnggota.toInt())
                DetailAnggotaTimUiState.Success(agt)
            } catch (e: Exception) {
                DetailAnggotaTimUiState.Error
            } catch (e: Exception) {
                DetailAnggotaTimUiState.Error
            }
        }
    }
}