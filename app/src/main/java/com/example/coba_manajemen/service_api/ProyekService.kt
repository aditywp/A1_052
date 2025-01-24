package com.example.coba_manajemen.service_api

import com.example.coba_manajemen.model.AllProyekResponse
import com.example.coba_manajemen.model.Proyek
import com.example.coba_manajemen.model.ProyekDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProyekService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Proyek endpoints
    @POST("proyek/store")
    suspend fun insertProyek(@Body proyek: Proyek)

    @GET("proyek/.")
    suspend fun getAllProyek(): AllProyekResponse

    @GET("proyek/{id_proyek}")
    suspend fun getProyekById(@Path("id_proyek") idProyek: Int): ProyekDetailResponse

    @PUT("proyek/{id_proyek}")
    suspend fun updateProyek(@Path("id_proyek") idProyek: Int, @Body proyek: Proyek)

    @DELETE("proyek/{id_proyek}")
    suspend fun deleteProyek(@Path("id_proyek") idProyek: Int): Response<Void>
}
