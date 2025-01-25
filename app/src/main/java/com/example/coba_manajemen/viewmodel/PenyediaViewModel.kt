package com.example.coba_manajemen.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.coba_manajemen.ManajemenApplications
import com.example.coba_manajemen.viewmodel.proyek.DetailProyekViewModel
import com.example.coba_manajemen.viewmodel.proyek.HomeProyekViewModel
import com.example.coba_manajemen.viewmodel.proyek.InsertProyekViewModel
import com.example.coba_manajemen.viewmodel.proyek.UpdateProyekViewModel
import com.example.coba_manajemen.viewmodel.tim.DetailTimViewModel
import com.example.coba_manajemen.viewmodel.tim.HomeTimViewModel
import com.example.coba_manajemen.viewmodel.tim.InsertTimViewModel
import com.example.coba_manajemen.viewmodel.tim.UpdateTimViewModel


object PenyediaViewModel {
    val Factory = viewModelFactory {
        //HOME PROYEK
        initializer { HomeProyekViewModel(aplikasiManajemen().container.proyekRepository) }
        initializer { InsertProyekViewModel(aplikasiManajemen().container.proyekRepository) }
        initializer {
            DetailProyekViewModel(
                createSavedStateHandle(),
                aplikasiManajemen().container.proyekRepository
            )
        }
        initializer {
            UpdateProyekViewModel(
                createSavedStateHandle(),
                aplikasiManajemen().container.proyekRepository
            )
        }
        //HOME TIM
        initializer { HomeTimViewModel(aplikasiManajemen().container.timRepository) }
        initializer { InsertTimViewModel(aplikasiManajemen().container.timRepository) }
        initializer {
            DetailTimViewModel(
                createSavedStateHandle(),
                aplikasiManajemen().container.timRepository
            )
        }
        initializer {
            UpdateTimViewModel(
                createSavedStateHandle(),
                aplikasiManajemen().container.timRepository
            )
        }
    }
}

fun CreationExtras.aplikasiManajemen(): ManajemenApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ManajemenApplications)