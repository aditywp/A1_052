package com.example.coba_manajemen.repository

import com.example.coba_manajemen.model.AllTimResponse
import com.example.coba_manajemen.model.Tim
import com.example.coba_manajemen.service_api.TimService
import okio.IOException

interface TimRepository {
    suspend fun insertTim(tim: Tim)
    suspend fun getAllTim(): AllTimResponse
    suspend fun getTimById(idTim: Int): Tim
    suspend fun updateTim(idTim: Int, tim: Tim)
    suspend fun deleteTim(idTim: Int)
}

class NetworkTimRepository(
    private val timApiService: TimService
) : TimRepository {
    // Tim functions
    override suspend fun insertTim(tim: Tim) {
        timApiService.insertTim(tim)
    }

    override suspend fun getAllTim(): AllTimResponse =
        timApiService.getAllTim()

    override suspend fun getTimById(idTim: Int): Tim {
        return timApiService.getTimById(idTim).data
    }

    override suspend fun updateTim(idTim: Int, tim: Tim) {
        timApiService.updateTim(idTim, tim)
    }

    override suspend fun deleteTim(idTim: Int) {
        try {
            val response = timApiService.deleteTim(idTim)
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
