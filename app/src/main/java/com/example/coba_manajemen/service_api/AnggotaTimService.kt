package com.example.coba_manajemen.service_api

import com.example.coba_manajemen.model.AllAnggotaTimResponse
import com.example.coba_manajemen.model.AnggotaTim
import com.example.coba_manajemen.model.AnggotaTimDetailResponse
import retrofit2.Response
import retrofit2.http.*

interface AnggotaTimService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Anggota Tim endpoints
    @POST("anggotatim/store")
    suspend fun insertAnggotaTim(@Body anggotaTim: AnggotaTim)

    @GET("anggotaTim/.")
    suspend fun getAllAnggotaTim(): AllAnggotaTimResponse

    @GET("anggotatim/{id_anggota}")
    suspend fun getAnggotaTimById(@Path("id_anggota") idAnggota: Int): AnggotaTimDetailResponse

    @PUT("anggotatim/{id_anggota}")
    suspend fun updateAnggotaTim(@Path("id_anggota") idAnggota: Int, @Body anggotaTim: AnggotaTim)

    @DELETE("anggotatim/{id_anggota}")
    suspend fun deleteAnggotaTim(@Path("id_anggota") idAnggota: Int): Response<Void>
}
