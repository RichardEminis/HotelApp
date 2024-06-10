package com.example.hotelapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Hotel(
    val id: Int,
    val name: String,
    val address: String,
    val stars: Float,
    val distance: Float,
    val suitesAvailability: String? = null,
    var image: String? = null,
)