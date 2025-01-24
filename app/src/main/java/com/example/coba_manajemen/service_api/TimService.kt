package com.example.coba_manajemen.service_api

import com.example.coba_manajemen.model.AllTimResponse
import com.example.coba_manajemen.model.Tim
import com.example.coba_manajemen.model.TimDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TimService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Tim endpoints
    @POST("tim/store")
    suspend fun insertTim(@Body tim: Tim)

    @GET("tim/.")
    suspend fun getAllTim(): AllTimResponse

    @GET("tim/{id_tim}")
    suspend fun getTimById(@Path("id_tim") idTim: Int): TimDetailResponse

    @PUT("tim/{id_tim}")
    suspend fun updateTim(@Path("id_tim") idTim: Int, @Body tim: Tim)

    @DELETE("tim/{id_tim}")
    suspend fun deleteTim(@Path("id_tim") idTim: Int): Response<Void>
}