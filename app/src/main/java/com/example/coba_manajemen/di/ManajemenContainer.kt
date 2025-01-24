package com.example.coba_manajemen.di

import com.example.coba_manajemen.repository.AnggotaTimRepository
import com.example.coba_manajemen.repository.NetworkAnggotaTimRepository
import com.example.coba_manajemen.repository.NetworkProyekRepository
import com.example.coba_manajemen.repository.NetworkTimRepository
import com.example.coba_manajemen.repository.ProyekRepository
import com.example.coba_manajemen.repository.TimRepository
import com.example.coba_manajemen.service_api.AnggotaTimService
import com.example.coba_manajemen.service_api.ProyekService
import com.example.coba_manajemen.service_api.TimService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val proyekRepository: ProyekRepository
    val timRepository: TimRepository
    val anggotaTimRepository: AnggotaTimRepository
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

    //TIM
    private val timService: TimService by lazy {
        retrofit.create(TimService::class.java)
    }
    override val timRepository: TimRepository by lazy {
        NetworkTimRepository(timService)
    }

    //ANGGOTA
    private val anggotaTimService: AnggotaTimService by lazy {
        retrofit.create(AnggotaTimService::class.java)
    }
    override val anggotaTimRepository: AnggotaTimRepository by lazy {
        NetworkAnggotaTimRepository(anggotaTimService)
    }
}

