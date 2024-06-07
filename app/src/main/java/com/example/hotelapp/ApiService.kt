package com.example.cleverpumpkinhotel

import com.example.cleverpumpkinhotel.model.Hotel
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("0777.json")
    suspend fun getHotels(): List<Hotel>

    @GET("{id}.json")
    suspend fun getHotelDetails(@Path("id") id: String): Hotel
}