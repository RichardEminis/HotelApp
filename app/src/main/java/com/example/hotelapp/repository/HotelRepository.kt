package com.example.hotelapp.repository

import android.util.Log
import com.example.hotelapp.model.Hotel
import com.example.hotelapp.model.HotelDetail
import com.example.hotelapp.network.ApiService
import com.example.hotelapp.utils.HOTEL_DETAIL_URL
import com.example.hotelapp.utils.HOTEL_LIST_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HotelRepository @Inject constructor(private val apiService: ApiService) {

    private val ioDispatcher: CoroutineContext = Dispatchers.IO

    suspend fun getHotels(): List<Hotel> {
        return withContext(ioDispatcher) {
            val hotels = apiService.getHotels()
            hotels.forEach { hotel ->
                val detail = getHotelDetails(hotel.id)
                hotel.image = HOTEL_DETAIL_URL + detail.image
            }
            hotels
        }
    }

    suspend fun getHotelDetails(hotelId: Int): HotelDetail {
        return withContext(ioDispatcher) {
            apiService.getHotelDetails(hotelId)
        }
    }
}