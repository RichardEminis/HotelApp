package com.example.hotelapp.repository

import android.content.Context
import android.util.Log
import com.example.hotelapp.model.Hotel
import com.example.hotelapp.network.ApiService
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response

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
            service.getHotels()
        }
    }

    suspend fun getHotelDetails(hotelId: Int): Hotel {
        return withContext(Dispatchers.IO) {
            service.getHotelDetails(hotelId)
        }
    }
}