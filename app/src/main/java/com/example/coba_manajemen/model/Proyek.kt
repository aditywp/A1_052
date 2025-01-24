package com.example.coba_manajemen.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllProyekResponse(
    val status: Boolean,
    val message: String,
    val data: List<Proyek>
)

@Serializable
data class ProyekDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Proyek
)

@Serializable
data class Proyek(
    @SerialName("id_proyek")
    val idProyek: Int,

    @SerialName("nama_proyek")
    val namaProyek: String,

    @SerialName("deskripsi_proyek")
    val deskripsiProyek: String,

    @SerialName("tanggal_mulai")
    val tanggalMulai: String,

    @SerialName("tanggal_berakhir")
    val tanggalBerakhir: String,

    @SerialName("status_proyek")
    val statusProyek: String
)
