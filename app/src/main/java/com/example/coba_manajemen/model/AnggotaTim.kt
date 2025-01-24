package com.example.coba_manajemen.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllAnggotaTimResponse(
    val status: Boolean,
    val message: String,
    val data: List<AnggotaTim>
)

@Serializable
data class AnggotaTimDetailResponse(
    val status: Boolean,
    val message: String,
    val data: AnggotaTim
)

@Serializable
data class AnggotaTim(
    @SerialName("id_anggota")
    val idAnggota: Int,

    @SerialName("id_tim")
    val idTim: Int,

    @SerialName("nama_anggota")
    val namaAnggota: String,

    val peran: String
)
