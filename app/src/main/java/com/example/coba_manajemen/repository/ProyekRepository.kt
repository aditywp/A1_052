package com.example.coba_manajemen.repository

import com.example.coba_manajemen.model.AllProyekResponse
import com.example.coba_manajemen.model.Proyek
import com.example.coba_manajemen.service_api.ProyekService
import okio.IOException

interface ProyekRepository {
    suspend fun insertProyek(proyek: Proyek)
    suspend fun getAllProyek(): AllProyekResponse
    suspend fun getProyekById(idProyek: Int): Proyek
    suspend fun updateProyek(idProyek: Int, proyek: Proyek)
    suspend fun deleteProyek(idProyek: Int)
}

class NetworkProyekRepository(
    private val proyekApiService: ProyekService
) : ProyekRepository {

    // Proyek functions
    override suspend fun insertProyek(proyek: Proyek) {
        proyekApiService.insertProyek(proyek)
    }

    override suspend fun getAllProyek(): AllProyekResponse =
        proyekApiService.getAllProyek()

    override suspend fun getProyekById(idProyek: Int): Proyek {
        return proyekApiService.getProyekById(idProyek).data
    }

    override suspend fun updateProyek(idProyek: Int, proyek: Proyek) {
        proyekApiService.updateProyek(idProyek, proyek)
    }

    override suspend fun deleteProyek(idProyek: Int) {
        try {
            val response = proyekApiService.deleteProyek(idProyek)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete project. HTTP Status code:" +
                        " ${response.code()}")
            }else{
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
