package com.example.coba_manajemen.viewmodel.tim

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coba_manajemen.model.Tim
import com.example.coba_manajemen.repository.TimRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeTimUiState{
    data class Success(val tim: List<Tim>): HomeTimUiState()
    object Error: HomeTimUiState()
    object Loading: HomeTimUiState()
}

class HomeTimViewModel(
    private val tm: TimRepository
): ViewModel(){
    var tmUiState: HomeTimUiState by mutableStateOf(HomeTimUiState.Loading)
        private set

    init {
        getallTim()
    }

    fun getallTim(){
        viewModelScope.launch {
            tmUiState = HomeTimUiState.Loading
            tmUiState = try {
                HomeTimUiState.Success(tm.getAllTim().data)
            }catch (e: IOException) {
                HomeTimUiState.Error
            }catch (e: IOException) {
                HomeTimUiState.Error
            }
        }
    }

    fun deleteTim(idTim: Int){
        viewModelScope.launch {
            try {
                tm.deleteTim(idTim)
            }catch (e: IOException){
                HomeTimUiState.Error
            }catch (e: HttpException){
                HomeTimUiState.Error
            }
        }
    }
}