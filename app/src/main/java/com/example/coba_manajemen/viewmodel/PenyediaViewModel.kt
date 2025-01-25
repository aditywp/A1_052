package com.example.coba_manajemen.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.coba_manajemen.ManajemenApplications
import com.example.coba_manajemen.viewmodel.anggotatim.DetailAnggotaTimViewModel
import com.example.coba_manajemen.viewmodel.anggotatim.HomeAnggotaTimViewModel
import com.example.coba_manajemen.viewmodel.anggotatim.InsertAnggotaTimViewModel
import com.example.coba_manajemen.viewmodel.anggotatim.UpdateAnggotaTimViewModel
import com.example.coba_manajemen.viewmodel.proyek.DetailProyekViewModel
import com.example.coba_manajemen.viewmodel.proyek.HomeProyekViewModel
import com.example.coba_manajemen.viewmodel.proyek.InsertProyekViewModel
import com.example.coba_manajemen.viewmodel.proyek.UpdateProyekViewModel
import com.example.coba_manajemen.viewmodel.tim.DetailTimViewModel
import com.example.coba_manajemen.viewmodel.tim.HomeTimViewModel
import com.example.coba_manajemen.viewmodel.tim.InsertTimViewModel
import com.example.coba_manajemen.viewmodel.tim.UpdateTimViewModel
import com.example.coba_manajemen.viewmodel.tugas.DetailTugasViewModel
import com.example.coba_manajemen.viewmodel.tugas.HomeTugasViewModel
import com.example.coba_manajemen.viewmodel.tugas.InsertTugasViewModel
import com.example.coba_manajemen.viewmodel.tugas.UpdateTugasViewModel


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

        //HOME TUGAS
        initializer { HomeTugasViewModel(aplikasiManajemen().container.tugasRepository) }
        initializer {
            InsertTugasViewModel(
                aplikasiManajemen().container.tugasRepository,
                aplikasiManajemen().container.timRepository,
                aplikasiManajemen().container.proyekRepository
            )
        }
        initializer {
            DetailTugasViewModel(
                createSavedStateHandle(),
                aplikasiManajemen().container.tugasRepository,
                aplikasiManajemen().container.timRepository,
                aplikasiManajemen().container.proyekRepository
            )
        }
        initializer {
            UpdateTugasViewModel(
                createSavedStateHandle(),
                aplikasiManajemen().container.tugasRepository,
                aplikasiManajemen().container.timRepository,
                aplikasiManajemen().container.proyekRepository
            )
        }

        //ANGGOTA
        initializer { HomeAnggotaTimViewModel(aplikasiManajemen().container.anggotaTimRepository) }
        initializer {
            InsertAnggotaTimViewModel(
                aplikasiManajemen().container.anggotaTimRepository,
                aplikasiManajemen().container.timRepository)
        }
        initializer {
            DetailAnggotaTimViewModel(
                createSavedStateHandle(),
                aplikasiManajemen().container.anggotaTimRepository,
                aplikasiManajemen().container.timRepository
            )
        }
        initializer {
            UpdateAnggotaTimViewModel(
                createSavedStateHandle(),
                aplikasiManajemen().container.anggotaTimRepository,
                aplikasiManajemen().container.timRepository
            )
        }
    }
}

fun CreationExtras.aplikasiManajemen(): ManajemenApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ManajemenApplications)