package com.example.coba_manajemen.viewmodel.tugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coba_manajemen.model.Tugas
import com.example.coba_manajemen.repository.TugasRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException


sealed class HomeTugasUiState{
    data class Success(val tugas: List<Tugas>): HomeTugasUiState()
    object Error: HomeTugasUiState()
    object Loading: HomeTugasUiState()
}

class HomeTugasViewModel(
    private val tgs: TugasRepository
): ViewModel(){
    var tgsUiState: HomeTugasUiState by mutableStateOf(HomeTugasUiState.Loading)
        private set

    init {
        getallTugas()
    }

    fun getallTugas(){
        viewModelScope.launch {
            tgsUiState = HomeTugasUiState.Loading
            tgsUiState = try {
                HomeTugasUiState.Success(tgs.getAllTugas().data)
            }catch (e: IOException) {
                HomeTugasUiState.Error
            }catch (e: IOException) {
                HomeTugasUiState.Error
            }
        }
    }

    fun deleteTugas(idTim: Int){
        viewModelScope.launch {
            try {
                tgs.deleteTugas(idTim)
            }catch (e: IOException){
                HomeTugasUiState.Error
            }catch (e: HttpException){
                HomeTugasUiState.Error
            }
        }
    }
}