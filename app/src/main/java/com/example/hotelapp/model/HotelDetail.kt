package com.example.hotelapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HotelDetail(
    val id: Int,
    val name: String,
    val address: String,
    val stars: Float,
    val distance: Float,
    @SerialName("suites_availability") val suitesAvailability: String? = null,
    val image: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
)