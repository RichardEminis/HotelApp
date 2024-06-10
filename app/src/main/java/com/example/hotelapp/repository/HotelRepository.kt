package com.example.hotelapp.repository

import android.util.Log
import com.example.hotelapp.model.Hotel
import com.example.hotelapp.model.HotelDetail
import com.example.hotelapp.network.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class HotelRepository {
    private val logInterceptor = HttpLoggingInterceptor { message ->
        Log.d("OkHttp", message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logInterceptor)
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val contentType = "application/json".toMediaType()
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/iMofas/ios-android-test/master/")
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
    private val service: ApiService = retrofit.create(ApiService::class.java)

    suspend fun getHotels(): List<Hotel> {
        return withContext(Dispatchers.IO) {
            val hotels = service.getHotels()
            hotels.forEach { hotel ->
                val detail = getHotelDetails(hotel.id)
                hotel.image = "https://github.com/iMofas/ios-android-test/raw/master/${detail.image}"
            }
            hotels
        }
    }

    suspend fun getHotelDetails(hotelId: Int): HotelDetail {
        return withContext(Dispatchers.IO) {
            service.getHotelDetails(hotelId)
        }
    }
}