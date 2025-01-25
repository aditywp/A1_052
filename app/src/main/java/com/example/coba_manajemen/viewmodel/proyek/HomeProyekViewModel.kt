package com.example.coba_manajemen.viewmodel.proyek

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coba_manajemen.model.Proyek
import com.example.coba_manajemen.repository.ProyekRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException


sealed class HomeUiState{
    data class Success(val proyek: List<Proyek>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeProyekViewModel(
    private val pryk: ProyekRepository
): ViewModel(){
    var prykUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getAllPryk()
    }

    fun getAllPryk(){
        viewModelScope.launch {
            prykUIState = HomeUiState.Loading
            prykUIState = try {
                HomeUiState.Success(pryk.getAllProyek().data)
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e: IOException){
                HomeUiState.Error
            }
        }
    }

    fun deletePryk(idProyek: Int){
        viewModelScope.launch {
            try {
                pryk.deleteProyek(idProyek)
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
}
