package com.example.coba_manajemen.di

import com.example.coba_manajemen.repository.NetworkProyekRepository
import com.example.coba_manajemen.repository.ProyekRepository
import com.example.coba_manajemen.service_api.ProyekService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val proyekRepository: ProyekRepository
}

class ManajemenContainer : AppContainer {

    private val baseUrl = "http://10.0.2.2:3000/api/" // Base URL for your API
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    //PROYEK
    private val proyekService: ProyekService by lazy {
        retrofit.create(ProyekService::class.java)
    }

    override val proyekRepository: ProyekRepository by lazy {
        NetworkProyekRepository(proyekService)
    }
}

