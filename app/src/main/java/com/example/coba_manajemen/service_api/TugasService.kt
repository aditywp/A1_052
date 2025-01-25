package com.example.coba_manajemen.service_api

import com.example.coba_manajemen.model.AllTugasResponse
import com.example.coba_manajemen.model.Tugas
import com.example.coba_manajemen.model.TugasDetailResponse
import retrofit2.Response
import retrofit2.http.*

interface TugasService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Tugas endpoints
    @POST("tugas/store")
    suspend fun insertTugas(@Body tugas: Tugas)

    @GET("tugas/.")
    suspend fun getAllTugas(): AllTugasResponse

    @GET("tugas/{id_tugas}")
    suspend fun getTugasById(@Path("id_tugas") idTugas: Int): TugasDetailResponse

    @PUT("tugas/{id_tugas}")
    suspend fun updateTugas(@Path("id_tugas") idTugas: Int, @Body tugas: Tugas)

    @DELETE("tugas/{id_tugas}")
    suspend fun deleteTugas(@Path("id_tugas") idTugas: Int): Response<Void>
}
