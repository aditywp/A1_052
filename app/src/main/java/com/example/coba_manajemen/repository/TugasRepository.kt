package com.example.coba_manajemen.repository


import com.example.coba_manajemen.model.AllTugasResponse
import com.example.coba_manajemen.model.Tugas
import com.example.coba_manajemen.service_api.TugasService
import okio.IOException


interface TugasRepository {
    suspend fun insertTugas( tugas : Tugas)
    suspend fun getAllTugas(): AllTugasResponse
    suspend fun getTugasById(idTugas: Int): Tugas
    suspend fun updateTugas(idTugas: Int, tim: Tugas)
    suspend fun deleteTugas(idTugas: Int)
}

class NetworkTugasRepository(
    private val tugasApiService: TugasService
) : TugasRepository {
    // Tugas functions
    override suspend fun insertTugas(tugas: Tugas) {
        tugasApiService.insertTugas(tugas)
    }

    override suspend fun getAllTugas(): AllTugasResponse =
        tugasApiService.getAllTugas()

    override suspend fun getTugasById(idTugas: Int): Tugas {
        return tugasApiService.getTugasById(idTugas).data
    }

    override suspend fun updateTugas(idTugas: Int, tugas: Tugas) {
        tugasApiService.updateTugas(idTugas, tugas)
    }

    override suspend fun deleteTugas(idTugas: Int) {
        try {
            val response = tugasApiService.deleteTugas(idTugas)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete team. HTTP Status code: " +
                        "${response.code()}")
            }else{
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }
}