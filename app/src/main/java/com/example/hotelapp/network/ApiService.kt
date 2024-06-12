package com.example.hotelapp.network

import com.example.hotelapp.model.Hotel
import com.example.hotelapp.model.HotelDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("0777.json")
    suspend fun getHotels(): List<Hotel>

    @GET("{id}.json")
    suspend fun getHotelDetails(@Path("id") id: Int): HotelDetail
}