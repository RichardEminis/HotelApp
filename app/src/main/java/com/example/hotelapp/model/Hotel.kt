package com.example.hotelapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hotel(
    val id: Int,
    val name: String,
    val address: String,
    val stars: Float,
    val distance: Float,
    @SerialName("suites_availability") val suitesAvailability: String? = null,
    var image: String? = null,
) {
    fun getAvailableSuitesCount(): Int {
        return suitesAvailability?.split(":")?.size ?: 0
    }
}