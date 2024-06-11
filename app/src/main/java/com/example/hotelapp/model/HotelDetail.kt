package com.example.hotelapp.model

import kotlinx.serialization.Serializable

@Serializable
data class HotelDetail(
    val id: Int,
    val name: String,
    val address: String,
    val stars: Float,
    val distance: Float,
    val suitesAvailability: String? = null,
    val image: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
)