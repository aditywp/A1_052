package com.example.coba_manajemen.repository

import com.example.coba_manajemen.model.AllAnggotaTimResponse
import com.example.coba_manajemen.model.AnggotaTim
import com.example.coba_manajemen.service_api.AnggotaTimService
import okio.IOException


interface AnggotaTimRepository {
    suspend fun insertAnggotaTim(anggota: AnggotaTim)
    suspend fun getAllAnggotaTim(): AllAnggotaTimResponse
    suspend fun getAnggotaTimById(idAnggota: Int): AnggotaTim
    suspend fun updateAnggotaTim(idAnggota: Int, anggota: AnggotaTim)
    suspend fun deleteAnggotaTim(idAnggota: Int)
}

class NetworkAnggotaTimRepository(
    private val anggotaTimApiService: AnggotaTimService
) : AnggotaTimRepository {
    // AnggotaTim functions
    override suspend fun insertAnggotaTim(anggota: AnggotaTim) {
        anggotaTimApiService.insertAnggotaTim(anggota)
    }

    override suspend fun getAllAnggotaTim(): AllAnggotaTimResponse =
        anggotaTimApiService.getAllAnggotaTim()

    override suspend fun getAnggotaTimById(idAnggota: Int): AnggotaTim {
        return anggotaTimApiService.getAnggotaTimById(idAnggota).data
    }

    override suspend fun updateAnggotaTim(idAnggota: Int, anggota: AnggotaTim){
        anggotaTimApiService.updateAnggotaTim(idAnggota, anggota)
    }

    override suspend fun deleteAnggotaTim(idAnggota: Int) {
        try {
            val response = anggotaTimApiService.deleteAnggotaTim(idAnggota)
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