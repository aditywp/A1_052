package com.example.coba_manajemen.viewmodel.anggotatim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coba_manajemen.model.AnggotaTim
import com.example.coba_manajemen.repository.AnggotaTimRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeAnggotaTimUiState{
    data class Success(val anggota: List<AnggotaTim>): HomeAnggotaTimUiState()
    object Error: HomeAnggotaTimUiState()
    object Loading: HomeAnggotaTimUiState()
}

class HomeAnggotaTimViewModel(
    private val agt: AnggotaTimRepository
): ViewModel(){
    var agtUiState: HomeAnggotaTimUiState by mutableStateOf(HomeAnggotaTimUiState.Loading)
        private set

    init {
        getallAnggotaTim()
    }

    fun getallAnggotaTim(){
        viewModelScope.launch {
            agtUiState = HomeAnggotaTimUiState.Loading
            agtUiState = try {
                HomeAnggotaTimUiState.Success(agt.getAllAnggotaTim().data)
            }catch (e: IOException) {
                HomeAnggotaTimUiState.Error
            }catch (e: IOException) {
                HomeAnggotaTimUiState.Error
            }
        }
    }

    fun deleteAnggotaTim(idTim: Int){
        viewModelScope.launch {
            try {
                agt.deleteAnggotaTim(idTim)
            }catch (e: IOException){
                HomeAnggotaTimUiState.Error
            }catch (e: HttpException){
                HomeAnggotaTimUiState.Error
            }
        }
    }
}